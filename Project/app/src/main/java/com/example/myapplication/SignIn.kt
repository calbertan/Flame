package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Database.*

class SignIn: AppCompatActivity() {
    private lateinit var welcome: Button
    private lateinit var signUp: TextView
    private lateinit var databaseDao: UserDatabaseDao
    private lateinit var repository: UserRepository
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        if (supportActionBar != null){
            supportActionBar?.hide();
        }

        welcome = findViewById(R.id.welcome)
        welcome.setOnClickListener() {
            val UserNameEmailField:TextView = findViewById(R.id.UserNameEmailField)
            val usernameEmail:String = UserNameEmailField.text.toString()

            val PasswordField:TextView = findViewById(R.id.InPasswordField)
            val password:String = PasswordField.text.toString()


            databaseDao = UserDatabase.getInstance(this).userDatabaseDao
            repository = UserRepository(databaseDao)
            factory = UserViewModelFactory(repository)
            viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

            var newNameEmail = false

            val t = Thread(Runnable{
                if (databaseDao.getUserIdByUserInputNameOrEmail(usernameEmail) == null ) {
                    newNameEmail = true
                }
//                if (databaseDao.emailExists(password) == null) {
//                    newNameEmail = true
//                }
            })
            t.start()
            t.join()

            if(newNameEmail == false){
                // check password here
                var passwordCorrect = false
                val p = Thread(Runnable{
                    if (databaseDao.getPasswordByUserInputNameOrEmail(usernameEmail) == password) {
                        passwordCorrect = true
                    }
                })
                p.start()
                p.join()

                if(passwordCorrect == true){
                    val intent = Intent(this, Activity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(applicationContext, "password not correct", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(applicationContext, "no such user", Toast.LENGTH_SHORT).show()
            }

        }

        signUp = findViewById(R.id.no_Account_Signup)
        signUp.setOnClickListener() {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}