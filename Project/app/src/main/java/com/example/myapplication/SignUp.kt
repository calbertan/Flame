package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignUp: AppCompatActivity() {
    private lateinit var burnning:TextView
    private lateinit var signIn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)
        if (supportActionBar != null){
            supportActionBar?.hide();
        }

        burnning = findViewById(R.id.burnning)
        burnning.setOnClickListener() {
            val intent = Intent(this, Activity::class.java)
            startActivity(intent)
        }

        signIn = findViewById(R.id.have_Account_Signin)
        signIn.setOnClickListener() {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }
    }
}