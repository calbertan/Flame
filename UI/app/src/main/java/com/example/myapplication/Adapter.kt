package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class Adapter(activity: FragmentActivity, inputList: ArrayList<Fragment>): FragmentStateAdapter(activity) {
    var list: ArrayList<Fragment>
    init {
        list = inputList
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }
}