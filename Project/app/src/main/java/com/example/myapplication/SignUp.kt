package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.room.ColumnInfo
import com.example.myapplication.Database.*

class SignUp: AppCompatActivity() {
    private lateinit var burnning:TextView
    private lateinit var name:TextView
    private lateinit var email:TextView
    private lateinit var password:TextView
    private lateinit var database: TicketAndUserDatabase
    private lateinit var databaseDao: UserDatabaseDao
    private lateinit var repository: UserRepository
    private lateinit var viewModel: UserViewModel
    private lateinit var factory: UserViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        if (supportActionBar != null){
            supportActionBar?.hide();
        }

        burnning = findViewById(R.id.burnning)


        burnning.setOnClickListener() {
            name = findViewById(R.id.UserName_Field)
            email = findViewById(R.id.Email_Field)

            val newUser= User(
                name = name.text.toString(),
                password = email.text.toString(),
                email = "",
            )
            println("debug: id=".plus(newUser.user_id))
            println("debug: name=".plus(newUser.name))

            //insert to database, might need to combine databasedao and repository
//            database = TicketAndUserDatabase.getInstance(this)
//            databaseDao = database.userDatabaseDao
//            repository = UserRepository(databaseDao)
//
//            factory = UserRepository(repository)
//            viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
//            viewModel.insert(newUser)

            val intent = Intent(this, Activity::class.java)
            startActivity(intent)
        }
    }
}