package com.example.myapplication

//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.core.graphics.drawable.RoundedBitmapDrawable
//import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment

class Homepage: Fragment() {
//    private lateinit var iv: ImageView
//    private lateinit var bitmap: Bitmap
//    private lateinit var bitmap1:Bitmap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.homepage, container, false)
        /*iv = view.findViewById(R.id.rectangle_14)
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.rectangle_14)
        bitmap1 = RoundedBitmapDrawable(bitmap, 500, 300, 20, 3)*/
        return view
    }
}