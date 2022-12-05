package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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

            val descriptionField:EditText = view.findViewById(R.id.general_description_detail)
            val description:String = descriptionField.text.toString()
            descriptionField.getText().clear()

            val priceField:EditText = view.findViewById(R.id.expected_price_description)
            val price:String = priceField.text.toString()
            priceField.getText().clear()

            val dateField:EditText = view.findViewById(R.id.expired_date_description)
            val date:String = dateField.text.toString()
            dateField.getText().clear()

            val locationField:EditText = view.findViewById(R.id.location_description)
            val location:String = locationField.text.toString()
            locationField.getText().clear()

            val deliveryField:EditText = view.findViewById(R.id.delivery_method_description)
            val delivery:String = deliveryField.text.toString()
            deliveryField.getText().clear()


            var currentid:Long = 0L
            val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val currentUser:String? = sharedPreferences?.getString("USER_KEY",null)
            println("debug: $currentUser ")

            val t = Thread(Runnable{
                currentid = databaseDao.usernameExists(currentUser!!)!!
            })
            t.start()
            t.join()

            val ticket: Ticket = Ticket(
                time = date,
                location = location,
                price = price,
                description = description,
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