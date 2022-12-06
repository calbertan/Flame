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

class MyAdapter(private val context: Context, private var list: List<Ticket>): BaseAdapter() {
    private lateinit var databaseDao: UserDatabaseDao

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

        img_field.load(img)
        val t = Thread(Runnable{
            userName = databaseDao.getUserWithTickets(list[position].userId)[0].user.name
        })
        t.start()
        t.join()
        ticketDescription.text = desc
        priceView.text = "$ " + price.toString()
        ExpireDay.text = date
        name.text = userName

        return view
    }

    fun replaceList(newList:List<Ticket>){
        list = newList
    }
}