package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BAr: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bar)

        if (supportActionBar != null){
            supportActionBar?.hide();
        }
    }
}