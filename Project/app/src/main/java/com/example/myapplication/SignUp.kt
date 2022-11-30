package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignUp: AppCompatActivity() {
    private lateinit var burnning:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        if (supportActionBar != null){
            supportActionBar?.hide();
        }

        burnning = findViewById(R.id.burnning)
        burnning.setOnClickListener() {
            val intent = Intent(this, Activity::class.java)
            startActivity(intent)
        }
    }
}