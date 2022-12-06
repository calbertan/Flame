package com.example.myapplication

//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
//import android.widget.ImageView
//import androidx.core.graphics.drawable.RoundedBitmapDrawable
//import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.myapplication.Database.*
import com.example.myapplication.Database.Entities.Ticket
import kotlinx.android.synthetic.main.adapter_layout.*
import org.w3c.dom.Text

class Homepage: Fragment() {

//    private lateinit var iv: ImageView
//    private lateinit var bitmap: Bitmap
//    private lateinit var bitmap1:Bitmap
    private lateinit var listView: ListView
    private lateinit var myAdapter: MyAdapter
    private lateinit var arrayList: ArrayList<Ticket>
    private lateinit var databaseDao: UserDatabaseDao
    private lateinit var repository: UserRepository
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel
    private var userName = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.homepage, container, false)

      
        /*iv = view.findViewById(R.id.rectangle_14)
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.rectangle_14)
        bitmap1 = RoundedBitmapDrawable(bitmap, 500, 300, 20, 3)*/

        listView = view.findViewById(R.id.ticket_list)
        arrayList = ArrayList()
        myAdapter = MyAdapter(requireActivity(),arrayList)
        listView.adapter = myAdapter

        databaseDao = UserDatabase.getInstance(requireContext()).userDatabaseDao
        repository = UserRepository(databaseDao)
        factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTicket = myAdapter.getItem(position) as Ticket
            showDialog(selectedTicket)
        }



        viewModel.allLiveData.observe(requireActivity()){
            //update listView
            println("debug $it")
            myAdapter.replaceList(it)
            myAdapter.notifyDataSetChanged()
        }

        return view
    }

    private fun showDialog(ticket: Ticket){
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
            ticketBackground.load(ticket.ticketPhoto)
            descriptionView.text = ticket.description
            priceView.text = ticket.price.toString()
            dateView.text = ticket.time
            deliveryView.text = ticket.delivery
            locationView.text = ticket.location
            var lp = dialog.window?.attributes
            if (lp != null) {
                lp.width = (resources.displayMetrics.widthPixels * 0.95).toInt()
            }
            lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = lp
            dialog.show()
        }

    }
}