package com.example.myapplication

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.Database.Entities.Ticket

class MyAdapter(private val context: Context, private var list: List<Ticket>): BaseAdapter() {
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
        val view = View.inflate(context, R.layout.adapter_layout, null)

        //adapter layout is the layout of a single ticket

        val desc = list[position].description
        val location = list[position].location
        val price = list[position].price
        val date = list[position].time
        val delivery = list[position].delivery

        return view
    }

    fun replaceList(newList:List<Ticket>){
        list = newList
    }
}