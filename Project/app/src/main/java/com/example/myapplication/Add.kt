package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.myapplication.Database.*
import com.example.myapplication.Database.Entities.Ticket
import com.example.myapplication.Database.Entities.User
import kotlinx.coroutines.launch

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
            val price:Long = priceField.text.toString().toLong()
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

            lifecycleScope.launch {
                val ticket: Ticket = Ticket(
                    time = date,
                    location = location,
                    price = price,
                    description = description,
                    delivery = delivery,
                    status = 0,
                    userId = 0L,
                    buyerId = -1L,
                    sellerId = currentid,
                    ticketPhoto = getBitmap()
                )
                viewModel.insertTicket(ticket)
            }



        }

        return view
    }

    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

}