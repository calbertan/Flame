package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Database.*
import com.example.myapplication.Database.Entities.Ticket

class Bought: Fragment() {
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
        val view = inflater.inflate(R.layout.bought, container, false)

//        var currentid:Long = 0L
//        val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
//        val currentUser:String? = sharedPreferences?.getString("USER_KEY",null)
//        println("debug: $currentUser ")
//
//        val t = Thread(Runnable{
//            currentid = databaseDao.usernameExists(currentUser!!)!!
//        })
//
//        listView = view.findViewById(R.id.ticket_list)
//        arrayList = ArrayList()
//        myAdapter = MyAdapter(requireActivity(),arrayList)
//        listView.adapter = myAdapter
//        val myTickets: MutableList<Ticket> = mutableListOf<Ticket>()
//
//        databaseDao = UserDatabase.getInstance(requireContext()).userDatabaseDao
//        repository = UserRepository(databaseDao)
//        factory = UserViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
//
//        viewModel.allLiveData.observe(requireActivity()){
//            //update listView
//            for (i in 0..it.size) {
//                println("debug $it.")
//                if (it[i].buyerId == currentid)
//                    myTickets.add(it[i])
//            }
//            myAdapter.replaceList(myTickets)
//            myAdapter.notifyDataSetChanged()
//        }

        return view
    }
}