package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var imageButton: ImageButton
    private lateinit var add: Add
    private lateinit var homepage: Homepage
    private lateinit var setting: Setting
    private lateinit var fragments: ArrayList<Fragment>
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: Adapter
    private lateinit var tabConfigurationStrategy : TabLayoutMediator.TabConfigurationStrategy
    private val tabTitles = arrayOf("", "", "")
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private lateinit var start:TextView

    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        if (supportActionBar != null){
            supportActionBar?.hide();
        }
        start = findViewById(R.id.GetStarted)
        start.setOnClickListener() {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

//        imageButton = findViewById(R.id.btn_login)
//        imageButton.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                v.setBackgroundResource(R.mipmap.password)
//            } else if (event.action == MotionEvent.ACTION_UP) {
//                v.setBackgroundResource(R.mipmap.go)
//            }
//            false
//        }
    }

}