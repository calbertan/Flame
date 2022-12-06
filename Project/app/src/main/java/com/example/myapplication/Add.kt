package com.example.myapplication

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.myapplication.Database.*
import com.example.myapplication.Database.Entities.Ticket
import com.example.myapplication.Database.Entities.User
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Add: Fragment() {
    private lateinit var databaseDao: UserDatabaseDao
    private lateinit var repository: UserRepository
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel
    private lateinit var uploadImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.publish, container, false)

        uploadImage = view.findViewById(R.id.photo_background)
        uploadImage.setOnClickListener(){
            pickImageGallery()
        }

        val readyButton: TextView = view.findViewById(R.id.Ready)
        readyButton.setOnClickListener() {
            databaseDao = UserDatabase.getInstance(requireContext()).userDatabaseDao
            repository = UserRepository(databaseDao)
            factory = UserViewModelFactory(repository)
            viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)

            var valid = true

            var description: String=""
            val descriptionField:EditText = view.findViewById(R.id.general_description_detail)
            if(descriptionField.text.toString() == "") {
                Toast.makeText(context, "please enter a description", Toast.LENGTH_SHORT).show()
                valid = false
            }
            else {
                description = descriptionField.text.toString()
            }

            var price:Double = 0.0;
            val priceField:EditText = view.findViewById(R.id.expected_price_description)
            if(priceField.text.toString() == ""){
                Toast.makeText(context, "please enter a price", Toast.LENGTH_SHORT).show()
                valid = false
            }
            else{
                println("debug: ${priceField.text}")
                price = priceField.text.toString().toDouble()
            }


            val dateField:EditText = view.findViewById(R.id.expired_date_description)
            val date:String = dateField.text.toString()

            val locationField:EditText = view.findViewById(R.id.location_description)
            val location:String = locationField.text.toString()

            val deliveryField:EditText = view.findViewById(R.id.delivery_method_description)
            val delivery:String = deliveryField.text.toString()

            var currentid:Long = 0L
            val sharedPreferences = this.activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            val currentUser:String? = sharedPreferences?.getString("USER_KEY",null)
            println("debug: $currentUser ")

            val t = Thread(Runnable{
                currentid = databaseDao.usernameExists(currentUser!!)!!
            })
            t.start()
            t.join()

            if(valid) {
                descriptionField.getText().clear()
                priceField.getText().clear()
                dateField.getText().clear()
                locationField.getText().clear()
                deliveryField.getText().clear()

                val t = Thread(Runnable {
                    currentid = databaseDao.usernameExists(currentUser!!)!!
                })
                t.start()
                t.join()
                
                lifecycleScope.launch{
                  val ticket: Ticket = Ticket(
                      time = date,
                      location = location,
                      price = price,
                      description = description,
                      delivery = delivery,
                      status = 0,
                      userId = 0L,
                      buyerId = -1L,
                      sellerId = currentid,
                      ticketPhoto = compressImage(uploadImage.drawable.toBitmap())!!
                  )
                  viewModel.insertTicket(ticket)
                }

            }
        }

        return view
    }

    private suspend fun getBitmap(): Bitmap {
        val loading = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
            .build()

        val result = (loading.execute(request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            uploadImage.setImageURI(data?.data)
        }
    }

//    fun resizePhoto(bitmap: Bitmap): Bitmap {
//        val w = bitmap.width
//        val h = bitmap.height
//        val aspRat = w / h
//        val H = 110
//        val W = H * aspRat
//        val b = Bitmap.createScaledBitmap(bitmap, W, H, false)
//
//        return b
//
//    }
private fun compressImage(image: Bitmap): Bitmap? {
    val baos = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    var options = 90
    val length = baos.toByteArray().size / 1024
    if (length > 5000) {
        //重置baos即清空baos
        baos.reset()
        //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, 10, baos)
    } else if (length > 4000) {
        baos.reset()
        image.compress(Bitmap.CompressFormat.JPEG, 20, baos)
    } else if (length > 3000) {
        baos.reset()
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos)
    } else if (length > 2000) {
        baos.reset()
        image.compress(Bitmap.CompressFormat.JPEG, 70, baos)
    }
    //循环判断如果压缩后图片是否大于1M,大于继续压缩
    while (baos.toByteArray().size / 1024 > 1024) {
        //重置baos即清空baos
        baos.reset()
        //这里压缩options%，把压缩后的数据存放到baos中
        image.compress(Bitmap.CompressFormat.JPEG, options, baos)
        //每次都减少10
        options -= 10
    }
    //把压缩后的数据baos存放到ByteArrayInputStream中
    val isBm = ByteArrayInputStream(baos.toByteArray())
    //把ByteArrayInputStream数据生成图片
    return BitmapFactory.decodeStream(isBm, null, null)
}
}