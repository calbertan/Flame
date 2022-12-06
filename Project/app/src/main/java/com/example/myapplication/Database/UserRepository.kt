package com.example.myapplication.Database

import androidx.room.Query
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
    fun updateTicketStatus(tid: Long, status: Int){
        CoroutineScope(IO).launch{
            databaseDao.updateTicketStatus(tid, status)
        }
    }
    fun updateTicketBuyer(tid: Long, bid: Long){
        CoroutineScope(IO).launch{
            databaseDao.updateTicketBuyer(tid, bid)
        }
    }
    fun updateBalance(id: Long, newBalance: Double){
        CoroutineScope(IO).launch {
            databaseDao.updateBalance(id, newBalance)
        }
    }

    fun balanceFromId(id: Long): Double{
        var balance = 0.0
        CoroutineScope(IO).launch {
            if (databaseDao.balanceFromId(id) != null)
                balance = databaseDao.balanceFromId(id)!!
        }
        return balance
    }

//    suspend fun getUserIdByUserInputNameOrEmail(nameEmail: String) : Long {
//        return databaseDao.getUserIdByUserInputNameOrEmail(nameEmail)
//    }


}