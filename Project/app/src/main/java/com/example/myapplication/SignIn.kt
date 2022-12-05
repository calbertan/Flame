package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignIn: AppCompatActivity() {
    private lateinit var welcome: TextView
    private lateinit var signUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        if (supportActionBar != null){
            supportActionBar?.hide();
        }

        welcome = findViewById(R.id.welcome)
        welcome.setOnClickListener() {
            val intent = Intent(this, Activity::class.java)
            startActivity(intent)
        }

        signUp = findViewById(R.id.no_Account_Signup)
        signUp.setOnClickListener() {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}