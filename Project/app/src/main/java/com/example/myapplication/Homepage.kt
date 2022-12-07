package com.example.myapplication

//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.*
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.myapplication.Database.*
import com.example.myapplication.Database.Entities.Ticket
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.adapter_layout.*
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class Homepage: Fragment() {

//    private lateinit var iv: ImageView
//    private lateinit var bitmap: Bitmap
//    private lateinit var bitmap1:Bitmap
    val calendar = Calendar.getInstance()
    private var year = 0
    private var month = 0
    private var day = 0
    private var hours = 0
    private var mins = 0
    private var secs = 0
    private lateinit var listView: ListView
    private lateinit var myAdapter: MyAdapter
    private lateinit var arrayList: ArrayList<Ticket>
    private lateinit var databaseDao: UserDatabaseDao
    private lateinit var repository: UserRepository
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel
    private var userName = ""
    private lateinit var currentUser: String
    private var currentid: Long = 0L
    private var balance: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.homepage, container, false)
        /*iv = view.findViewById(R.id.rectangle_14)
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.rectangle_14)
        bitmap1 = RoundedBitmapDrawable(bitmap, 500, 300, 20, 3)*/

        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        currentUser = sharedPreferences?.getString("USER_KEY",null)!!

        listView = view.findViewById(R.id.ticket_list)
        arrayList = ArrayList()
        myAdapter = MyAdapter(requireActivity(),arrayList)
        listView.adapter = myAdapter
        val myTickets: MutableList<Ticket> = mutableListOf<Ticket>()

        databaseDao = UserDatabase.getInstance(requireContext()).userDatabaseDao
        repository = UserRepository(databaseDao)
        factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        val t = Thread(Runnable {
            currentid = databaseDao.usernameExists(currentUser!!)!!
            balance = databaseDao.balanceFromUsername(currentUser)!!
        })
        t.start()
        t.join()

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTicket = myAdapter.getItem(position) as Ticket
            showDialog(selectedTicket, position)
        }

        viewModel.allLiveData.observe(requireActivity()){
            //update listView
            myTickets.clear()
            for (i in 0..(it.size-1)) {
                println("debug $it.")
                if (it[i].status == 0 && it[i].sellerId != currentid)
                    myTickets.add(it[i])
            }
            myAdapter.replaceList(myTickets)
            myAdapter.notifyDataSetChanged()
        }

        return view
    }

    private fun showDialog(ticket: Ticket, position: Int){
        val dialog = context?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.tickets_detailed_info)
            val ticketBackground = dialog.findViewById<ImageView>(R.id.individualTicketsInfo)
            val descriptionView = dialog.findViewById<TextView>(R.id.ticket_detail_description)
            val priceView = dialog.findViewById<TextView>(R.id.ticket_detail_price)
            val dateView = dialog.findViewById<TextView>(R.id.timeleft)
            val deliveryView = dialog.findViewById<TextView>(R.id.delivery)
            val locationView = dialog.findViewById<TextView>(R.id.Location)
            val button = dialog.findViewById<Button>(R.id.orderButton)
            setTodayDateTime()
            val inputFormat = SimpleDateFormat("yyyyMMddHHmmss")
            val ExpiredDate = inputFormat.parse(ticket.time)
            val todayDate = Date(year,month,day,hours,mins,secs)
            println("today's date: " + todayDate.year )
            val diff: Long =  todayDate.time - ExpiredDate.time
            val no_days = diff / (1000*60*60*24)

            ticketBackground.load(ticket.ticketPhoto)
            descriptionView.text = ticket.description
            priceView.text = ticket.price.toString()
            dateView.text = "$no_days Days before Event Begin"
            deliveryView.text = ticket.delivery
            locationView.text = ticket.location

            button.setOnClickListener(){
                if (balance != null) {
                    if(ticket.sellerId == currentid){
                        Toast.makeText(context, "You can't buy your own ticket!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    else if(balance >= ticket.price){
                        lifecycleScope.launch{
                            val sellerBalance = viewModel.balanceFromId(ticket.sellerId)
                            println("debug: $ticket.sellerId")
                            println("debug: $sellerBalance")
                            viewModel.updateTicketStatus(ticket.ticket_id,1)
                            viewModel.updateTicketBuyer(ticket.ticket_id, currentid)
                            viewModel.updateBalance(currentid, balance - ticket.price)
                            viewModel.updateBalance(ticket.sellerId, sellerBalance!! + ticket.price)
                        }
                        Toast.makeText(context, "ticket bought!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    else{
                        Toast.makeText(context, "insufficient balance", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
            }

            var lp = dialog.window?.attributes
            if (lp != null) {
                lp.width = (resources.displayMetrics.widthPixels * 0.95).toInt()
            }
            lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = lp
            dialog.show()
        }

    }

    //set today's date and time.
    fun setTodayDateTime() {
        year = calendar.get(Calendar.YEAR) - 1900
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DATE)
    }



}