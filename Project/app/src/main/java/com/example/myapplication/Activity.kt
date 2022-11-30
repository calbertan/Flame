package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Activity:AppCompatActivity() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        if (supportActionBar != null){
            supportActionBar?.hide();
        }

        add = Add()
        homepage = Homepage()
        setting = Setting()
        fragments = ArrayList()
        fragments.add(homepage)
        fragments.add(add)
        fragments.add(setting)

        tabLayout = findViewById(R.id.tab)
        adapter = Adapter(this, fragments)
        viewPager2 = findViewById(R.id.viewpager)
        viewPager2.adapter = adapter

        tabConfigurationStrategy = TabLayoutMediator.TabConfigurationStrategy() {
                tab: TabLayout.Tab, position: Int ->
            tab.text = tabTitles[position]
        }

        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, tabConfigurationStrategy)
        tabLayoutMediator.attach()
        tabLayout.getTabAt(0)?.setIcon(R.mipmap.home)
        tabLayout.getTabAt(1)?.setIcon(R.mipmap.additem)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ticketbuy)
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }

    fun hello(view: View) {
        val intent = Intent(this, BAr::class.java)
        startActivity(intent)
    }
}