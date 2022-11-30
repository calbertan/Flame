package com.example.myapplication.Database

import androidx.lifecycle.*
import java.lang.IllegalArgumentException


class TicketViewModel(private val repository: TicketRepository) : ViewModel() {
    val allTicketsLiveData: LiveData<List<Ticket>> = repository.allTickets.asLiveData()

    fun insert(ticket: Ticket) {
        repository.insert(ticket)
    }

    fun deleteFirst(){
        val ticketList = allTicketsLiveData.value
        if (ticketList != null && ticketList.size > 0){
            val ticketId = ticketList[0].ticket_id
            repository.delete(ticketId)
        }
    }

    fun deleteAll(){
        val ticketList = allTicketsLiveData.value
        if (ticketList != null && ticketList.size > 0)
            repository.deleteAll()
    }
}

class TicketViewModelFactory (private val repository: TicketRepository) : ViewModelProvider.Factory {
    override fun<T: ViewModel> create(modelClass: Class<T>) : T{ //create() creates a new instance of the modelClass, which is TicketViewModel in this case.
        if(modelClass.isAssignableFrom(TicketViewModel::class.java))
            return TicketViewModel(repository) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}