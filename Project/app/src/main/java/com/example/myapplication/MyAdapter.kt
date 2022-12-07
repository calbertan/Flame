package com.example.myapplication

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import coil.load
import com.example.myapplication.Database.Entities.Ticket
import com.example.myapplication.Database.UserDatabase
import com.example.myapplication.Database.UserDatabaseDao
import java.text.SimpleDateFormat
import java.util.*

class MyAdapter(private val context: Context, private var list: List<Ticket>): BaseAdapter() {
    private lateinit var databaseDao: UserDatabaseDao
    val calendar = Calendar.getInstance()
    private var year = 0
    private var month = 0
    private var day = 0
    private var hours = 0
    private var mins = 0
    private var secs = 0
    private lateinit var ticketDescription: TextView
    private lateinit var priceView: TextView
    private lateinit var ExpireDay: TextView
    private lateinit var name: TextView
    private lateinit var userName: String

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val view = View.inflate(context, R.layout.individualinfo, null)
        var img_field : ImageView = view.findViewById(R.id.ticket_background)
        //adapter layout is the layout of a single ticket
        ticketDescription = view.findViewById(R.id.ticket_description)
        priceView = view.findViewById(R.id.price)
        ExpireDay = view.findViewById(R.id.ExpireDay)
        name = view.findViewById(R.id.name)

        databaseDao = UserDatabase.getInstance(context).userDatabaseDao

        val desc = list[position].description
        val location = list[position].location
        val price = list[position].price.toLong()
        val date = list[position].time
        val delivery = list[position].delivery
        val img = list[position].ticketPhoto
        print("id is:" + list[position].userId)

        setTodayDateTime()
        val inputFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val ExpiredDate = inputFormat.parse(date)
        val todayDate = Date(year,month,day,hours,mins,secs)
        println("today's date: " + todayDate.year )
        val diff: Long =  todayDate.time - ExpiredDate.time
        val no_days = diff / (1000*60*60*24)

        img_field.setImageBitmap(img)
        img_field.setBackgroundResource(R.drawable.corner)
        val t = Thread(Runnable{
            userName = databaseDao.getUserWithTickets(list[position].userId)[0].user.name
        })
        t.start()
        t.join()
        ticketDescription.text = desc
        priceView.text = "$ " + price.toString()
        ExpireDay.text = "$no_days Days before Event Begin"
        name.text = userName

        return view
    }

    fun replaceList(newList:List<Ticket>){
        list = newList
    }

    fun setTodayDateTime() {
        year = calendar.get(Calendar.YEAR) - 1900
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DATE)
    }
}