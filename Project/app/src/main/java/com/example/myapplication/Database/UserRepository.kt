package com.example.myapplication.Database

import com.example.myapplication.Database.Entities.Ticket
import com.example.myapplication.Database.Entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserRepository(private val databaseDao: UserDatabaseDao) {
    val allTickets: Flow<List<Ticket>> = databaseDao.getAllFlowTickets()

    fun insertUser(user: User){
        CoroutineScope(IO).launch{
            databaseDao.insertUser(user)
        }
    }

    fun insertTicket(ticket: Ticket){
        CoroutineScope(IO).launch{
            databaseDao.insertTicket(ticket)
        }
    }

//    suspend fun getUserIdByUserInputNameOrEmail(nameEmail: String) : Long {
//        return databaseDao.getUserIdByUserInputNameOrEmail(nameEmail)
//    }


}