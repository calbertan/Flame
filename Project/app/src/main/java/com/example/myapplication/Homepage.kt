package com.example.myapplication

//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
//import android.widget.ImageView
//import androidx.core.graphics.drawable.RoundedBitmapDrawable
//import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Database.*
import com.example.myapplication.Database.Entities.Ticket

class Homepage: Fragment() {

//    private lateinit var iv: ImageView
//    private lateinit var bitmap: Bitmap
//    private lateinit var bitmap1:Bitmap
    private lateinit var listView: ListView
    private lateinit var myAdapter: MyAdapter
    private lateinit var arrayList: ArrayList<Ticket>
    private lateinit var databaseDao: UserDatabaseDao
    private lateinit var repository: UserRepository
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.homepage, container, false)

      
        /*iv = view.findViewById(R.id.rectangle_14)
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.rectangle_14)
        bitmap1 = RoundedBitmapDrawable(bitmap, 500, 300, 20, 3)*/

        listView = view.findViewById(R.id.ticket_list)
        arrayList = ArrayList()
        myAdapter = MyAdapter(requireActivity(),arrayList)
        listView.adapter = myAdapter

        databaseDao = UserDatabase.getInstance(requireContext()).userDatabaseDao
        repository = UserRepository(databaseDao)
        factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

        viewModel.allLiveData.observe(requireActivity()){
            //update listView
            println("debug $it")
            myAdapter.replaceList(it)
            myAdapter.notifyDataSetChanged()
        }

        return view
    }
}