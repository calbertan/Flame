package com.example.myapplication.Database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

//A Repository manages queries and allows you to use multiple backends.
// In the most common example, the Repository implements the logic for
// deciding whether to fetch data from a network or use results cached in a local database.
class TicketRepository(private val ticketDatabaseDao: TicketDatabaseDao) {

    val allTickets: Flow<List<Ticket>> = ticketDatabaseDao.getAllTickets()

    fun insert(ticket: Ticket){
        CoroutineScope(IO).launch{
            ticketDatabaseDao.insertTicket(ticket)
        }
    }

    fun delete(id: Long){
        CoroutineScope(IO).launch {
            ticketDatabaseDao.deleteTicket(id)
        }
    }

    fun deleteAll(){
        CoroutineScope(IO).launch {
            ticketDatabaseDao.deleteAll()
        }
    }
}