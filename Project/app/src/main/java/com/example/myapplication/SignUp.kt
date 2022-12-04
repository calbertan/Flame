package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.Database.*
import com.example.myapplication.Database.Entities.User
import kotlinx.coroutines.launch

class SignUp: AppCompatActivity() {
    private lateinit var burnning:TextView
    private lateinit var signIn: TextView
    private lateinit var databaseDao: UserDatabaseDao
    private lateinit var repository: UserRepository
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        if (supportActionBar != null){
            supportActionBar?.hide();
        }

        burnning = findViewById(R.id.burnning)
        burnning.setOnClickListener() {
            val UserNameField:TextView = findViewById(R.id.UsernameField)
            val username:String = UserNameField.text.toString()

            val EmailField:TextView = findViewById(R.id.EmailField)
            val email:String = EmailField.text.toString()

            databaseDao = UserDatabase.getInstance(this).userDatabaseDao
            repository = UserRepository(databaseDao)
            factory = UserViewModelFactory(repository)
            viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

            var newUser = true

            lifecycleScope.launch {
                if(databaseDao.getUserIdByUserInputNameOrEmail(username) == null){
                    newUser = false
                    println("not new")
                }
            }

            if(newUser){
                val randId = (0..100000).random().toLong()
                val user: User = User(randId,username, "password", email)

                viewModel.insertUser(user)

                val intent = Intent(this, Activity::class.java)
                startActivity(intent)
            }
            else{
                println("debug: nothing")
            }

        }

        signIn = findViewById(R.id.have_Account_Signin)
        signIn.setOnClickListener() {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }
}