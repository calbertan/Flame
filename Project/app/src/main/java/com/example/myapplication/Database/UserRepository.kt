package com.example.myapplication.Database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//A Repository manages queries and allows you to use multiple backends.
// In the most common example, the Repository implements the logic for
// deciding whether to fetch data from a network or use results cached in a local database.
class UserRepository(private val userDatabaseDao: UserDatabaseDao) {

    val allUsers: Flow<List<User>> = userDatabaseDao.getAllUsers()

    fun insert(user: User){
        CoroutineScope(IO).launch{
            userDatabaseDao.insertUser(user)
        }
    }

    fun delete(id: Long){
        CoroutineScope(IO).launch {
            userDatabaseDao.deleteUser(id)
        }
    }

    fun deleteAll(){
        CoroutineScope(IO).launch {
            userDatabaseDao.deleteAll()
        }
    }
}