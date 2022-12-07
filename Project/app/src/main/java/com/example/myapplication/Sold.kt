package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.myapplication.Database.*
import com.example.myapplication.Database.Entities.Ticket

class Sold: Fragment() {
    private lateinit var listView: ListView
    private lateinit var myAdapter: MyAdapter
    private lateinit var arrayList: ArrayList<Ticket>
    private lateinit var databaseDao: UserDatabaseDao
    private lateinit var repository: UserRepository
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sold, container, false)

        var currentid:Long = 0L
        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val currentUser:String? = sharedPreferences?.getString("USER_KEY",null)

        listView = view.findViewById(R.id.ticket_list)
        arrayList = ArrayList()
        myAdapter = MyAdapter(requireActivity(),arrayList)
        listView.adapter = myAdapter
        val myTickets: MutableList<Ticket> = mutableListOf<Ticket>()

        databaseDao = UserDatabase.getInstance(requireContext()).userDatabaseDao
        repository = UserRepository(databaseDao)
        factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        val t = Thread(Runnable{
            currentid = databaseDao.usernameExists(currentUser!!)!!
        })
        t.start()
        t.join()

        viewModel.allLiveData.observe(requireActivity()){
            //update listView
            myTickets.clear()
            for (i in 0..(it.size-1)) {
                println("debug $it.")
                if (it[i].sellerId == currentid && it[i].status == 0)
                    myTickets.add(it[i])
            }
            myAdapter.replaceList(myTickets)
            myAdapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTicket = myAdapter.getItem(position) as Ticket
            showDialog(selectedTicket)
        }

        return view
    }

    private fun showDialog(ticket: Ticket){
        val dialog = context?.let { Dialog(it) }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.warning)

            val button_yes = dialog.findViewById<Button>(R.id.YesButton)
            val button_no = dialog.findViewById<Button>(R.id.NoButton)
            button_no.setOnClickListener {
                dialog.cancel()
            }
            button_yes.setOnClickListener {
                viewModel.deleteTicketById(ticket.ticket_id)
                dialog.cancel()
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

}