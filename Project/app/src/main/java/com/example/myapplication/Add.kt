package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Database.*
import com.example.myapplication.Database.Entities.Ticket
import com.example.myapplication.Database.Entities.User

class Add: Fragment() {
    private lateinit var databaseDao: UserDatabaseDao
    private lateinit var repository: UserRepository
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.publish, container, false)

        val readyButton: TextView = view.findViewById(R.id.Ready)
        readyButton.setOnClickListener() {

            databaseDao = UserDatabase.getInstance(requireContext()).userDatabaseDao
            repository = UserRepository(databaseDao)
            factory = UserViewModelFactory(repository)
            viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

            var globals = Globals()
            var currentUser = globals.currentUser
            println("debug: $currentUser")
            var currentid:Long = 0L

//            val t = Thread(Runnable{
//                currentid = databaseDao.usernameExists(currentUser)!!
//            })
//            t.start()
//            t.join()


            val ticket: Ticket = Ticket(
                time = "",
                location = "",
                price = "",
                description = "",
                status = 0,
                userId = 0L,
                buyerId = -1L,
                sellerId = currentid
            )
            viewModel.insertTicket(ticket)
        }

        return view
    }
}