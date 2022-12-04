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
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Database.Entities.Ticket
import com.example.myapplication.Database.Entities.User
import com.example.myapplication.Database.UserDatabase
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch


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
        //testing the database
//        val dao = UserDatabase.getInstance(this).userDatabaseDao
//
//        val users = listOf(
//            User(0,"Bruce", "123456", "wha61@sfu.ca" ),
//            User(1,"Ctan", "654321", "cat8@sfu.ca" ),
//            User(2,"delete", "654321", "cat8@sfu.ca" ),
//            User(3,"test", "654321", "cat8@sfu.ca" ),
//        )
//
//        val tickets = listOf(
//            // bruce's unsold ticket
//            Ticket(0, "12:00", "sfu", "100", "expensive", 0, 0, -1, 0),
//            // bruce's sold ticket
//            Ticket(1, "12:00", "surrey", "200", "more expensive", 1, 0, 1, 0),
//            // bruce's bought ticket
//            Ticket(2, "12:00", "bc", "300", "more expensive", 0, 1, 0, 1),
//        )
//
//        lifecycleScope.launch{
//            users.forEach { dao.insertUser(it)}
//            tickets.forEach { dao.insertTicket(it)}
//
//            // get bruce's user info and all related tickets info
//            val curUserTickets = dao.getUserWithTickets(0)
//            println("it is:: ts" + curUserTickets)
//
//            // get ctan id
//            val oneUserID = dao.getUserIdByUserInputNameOrEmail("Ctan")
//            println("it is:: id" + oneUserID)
//
//            // get ctan password
//            val oneUserPassword = dao.getPasswordByUserInputNameOrEmail("Ctan")
//            println("it is:: ps" + oneUserPassword)
//
//            // get bruce's ticket info
//            val curUsertickets = curUserTickets[0].tickets
//            println("it is:: curUserticket " + curUsertickets)
//
//            // get bruce's unsold ticket, buyerId is default -1, if it is changed, it is sold
//            val unsoldTicket = arrayListOf<Ticket>()
//            for(t in curUsertickets){
//                if(t.buyerId == -1L){
//                    unsoldTicket.add(t)
//                }
//            }
//            println("it is:: unsold ticket " + unsoldTicket)
//
//            // get bruce's published ticket, which is all bruce's related tickets
//            val publishedTicket = curUsertickets
//            println("it is:: published ticket " + publishedTicket)
//
//            // get bruce's bought tickets, which is get from all tickets in database, that the buyId is bruce's userID
//            val curUserId = 0L
//            val allTickets = dao.getAllTickets()
//            println("it is:: allTickets " + allTickets)
//            val boughtTicket = arrayListOf<Ticket>()
//            for(t in allTickets){
//                if(t.buyerId == curUserId){
//                    boughtTicket.add(t)
//                }
//            }
//            println("it is:: bought ticket " + boughtTicket)
//
//            // test for delete function
//            dao.deleteUser(2L)
//
//            // to get one ticket by id in database
//            val ticket0 = dao.getTicketById(0)
//            println("it is:: first ticket " + ticket0)
//
//        }
//
////        imageButton = findViewById(R.id.btn_login)
////        imageButton.setOnTouchListener { v, event ->
////            if (event.action == MotionEvent.ACTION_DOWN) {
////                v.setBackgroundResource(R.mipmap.password)
////            } else if (event.action == MotionEvent.ACTION_UP) {
////                v.setBackgroundResource(R.mipmap.go)
////            }
////            false
////        }
    }

}