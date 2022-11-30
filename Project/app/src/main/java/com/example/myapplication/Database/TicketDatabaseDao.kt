package com.example.myapplication.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//XD: At compile time, Room automatically generates implementations of the custom DAO interface that you define.
// Click the middle mouse button (Windows users) to see the code generated automatically by the system.
@Dao
interface TicketDatabaseDao {

    // for the publish page
    @Insert
    suspend fun insertTicket(ticket: Ticket)

    //A Flow is an async sequence of values
    //Flow produces values one at a time (instead of all at once) that can generate values
    //from async operations like network requests, database calls, or other async code.
    //It supports coroutines throughout its API, so you can transform a flow using coroutines as well!
    //Code inside the flow { ... } builder block can suspend. So the function is no longer marked with suspend modifier.
    //See more details here: https://kotlinlang.org/docs/flow.html#flows
    @Query("SELECT * FROM ticket_table")
    fun getAllTickets(): Flow<List<Ticket>>

    @Query("DELETE FROM ticket_table")
    suspend fun deleteAll()

    @Query("DELETE FROM ticket_table WHERE ticket_id = :key") //":" indicates that it is a Bind variable
    suspend fun deleteTicket(key: Long)


    // for the home page

    // get all the sell tickets, not include own tickets
    // this return tickets of all current on sell tickets, but not include current user's published tickets
    @Query("SELECT * FROM ticket_table WHERE ticket_status_column = :bool AND userId != :curUserID")
    fun getHomeShownTickets(bool: Boolean = false, curUserID : String): Flow<List<Ticket>>


    // for the manage page

    // get current user's published tickets
    // get all published tickets, include both sold and unsold tickets
    @Query("SELECT * FROM ticket_table WHERE sellerId = :curUserID")
    fun getPublishedTickets(curUserID : String): Flow<List<Ticket>>

    // get current user's bought tickets
    // get all the tickets that the buyerId is current user, which means all tickets bought by this user
    @Query("SELECT * FROM ticket_table WHERE buyerId = :curUserID")
    fun getBoughtTickets(curUserID : String): Flow<List<Ticket>>


}