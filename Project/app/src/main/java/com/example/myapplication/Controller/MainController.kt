package com.example.myapplication.Controller

import com.example.myapplication.Database.*

class MainController {
    private lateinit var database: TicketAndUserDatabase
    private lateinit var databaseDao: UserDatabaseDao
    private lateinit var repository: UserRepository
    private lateinit var viewModel: UserViewModel
    private lateinit var factory: UserViewModelFactory



    fun signUp(name:String , email:String){
        val newUser= User(
            name = name,
            password = email,
            email = "",
        )
        println("debug: id=".plus(newUser.user_id))
        println("debug: name=".plus(newUser.name))

        //insert to database, might need to combine databasedao and repository
//            database = TicketAndUserDatabase.getInstance(this)
//            databaseDao = database.userDatabaseDao
//            repository = UserRepository(databaseDao)
//
//            factory = UserRepository(repository)
//            viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
//            viewModel.insert(newUser)
    }
}