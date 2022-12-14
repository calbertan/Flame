package com.example.myapplication.Database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.myapplication.Database.Entities.Ticket
import com.example.myapplication.Database.Entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UserViewModel(private val repository: UserRepository) :ViewModel(){
    val allLiveData = repository.allTickets.asLiveData()

    fun insertUser(user: User){
        repository.insertUser(user)
    }
    fun insertTicket(ticket: Ticket){
        repository.insertTicket(ticket)
    }

    fun deleteTicketById(id: Long){
        repository.deleteTicketById(id)
    }

    fun updateTicketStatus(tid: Long, status: Int){
        repository.updateTicketStatus(tid, status)
    }

    fun updateTicketBuyer(tid: Long, bid: Long){
        repository.updateTicketBuyer(tid, bid)
    }

    fun updateBalance(id: Long, newBalance: Double){
        repository.updateBalance(id, newBalance)
    }
    fun balanceFromId(id: Long): Double{
        return repository.balanceFromId(id)

    }

//    suspend fun getUserIdByUserInputNameOrEmail(nameEmail: String) : Long {
//        return repository.getUserIdByUserInputNameOrEmail(nameEmail)
//    }
}

class UserViewModelFactory(private val repository: UserRepository)
    : ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass:Class<T>): T{
        if (modelClass.isAssignableFrom(UserViewModel::class.java))
            return UserViewModel(repository) as T
        throw IllegalArgumentException("Error")
    }
}