package com.example.myapplication.Database

import androidx.room.*
import com.example.myapplication.Database.Entities.Ticket
import com.example.myapplication.Database.Entities.User
import com.example.myapplication.Database.Entities.Relations.UserWithTickets
import kotlinx.coroutines.flow.Flow

//XD: At compile time, Room automatically generates implementations of the custom DAO interface that you define.
// Click the middle mouse button (Windows users) to see the code generated automatically by the system.
@Dao
interface UserDatabaseDao {

    // the insert is use for the sign up part
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    // for the publish page
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTicket(ticket: Ticket)

    // get one user by id and all associated tickets
    @Transaction
    @Query("SELECT * FROM user_table WHERE user_id = :userId")
    suspend fun getUserWithTickets(userId : Long): List<UserWithTickets>

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<User>

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM user_table WHERE user_id = :key") //":" indicates that it is a Bind variable
    suspend fun deleteUser(key: Long)

    @Query("SELECT * FROM ticket_table")
    suspend fun getAllTickets(): List<Ticket>

    @Query("SELECT * FROM ticket_table")
    fun getAllFlowTickets(): Flow<List<Ticket>>

    @Query("SELECT * FROM ticket_table WHERE ticket_id = :key")
    suspend fun getTicketById(key: Long): Ticket

    @Query("DELETE FROM ticket_table")
    suspend fun deleteAllTickets()

    @Query("DELETE FROM ticket_table WHERE ticket_id = :key") //":" indicates that it is a Bind variable
    suspend fun deleteTicket(key: Long)


    // for the sign in section

    /*
    * for the sign in part, when a user enter its name or email, check it in
    * the database if there are corresponding name or email
    * if exists, the following queries return the password and userId of that user
    * the password returned is used to check if the user's entered password is correct
    * the userid is for tickets
    * if not exist, then return null
    * */

    @Query("SELECT user_password_column FROM user_table WHERE user_name_column = :nameEmail OR user_email_column = :nameEmail")
    fun getPasswordByUserInputNameOrEmail(nameEmail: String) : String?

    @Query("SELECT user_id FROM user_table WHERE user_name_column = :nameEmail OR user_email_column = :nameEmail")
    fun getUserIdByUserInputNameOrEmail(nameEmail: String) : Long?

    @Query("SELECT user_id FROM user_table WHERE user_name_column = :name")
    fun usernameExists(name: String): Long?

    @Query("SELECT user_id FROM user_table WHERE user_email_column = :email")
    fun emailExists(email: String): Long?


// this part has move to the controller part, as these query can not be implement here

//    // for the home page
//
//    // get all the sell tickets, not include own tickets
//    // this return tickets of all current on sell tickets, but not include current user's published tickets
//    @Query("SELECT * FROM ticket_table WHERE ticket_status_column = 0 AND userId != :curUserID")
//    suspend fun getHomeShownTickets(curUserID : String): Flow<List<Ticket>>

//
//    // for the manage page
//
//    // get current user's published tickets
//    // get all published tickets, include both sold and unsold tickets
//    @Query("SELECT * FROM ticket_table WHERE sellerId = :curUserID")
//    suspend fun getPublishedTickets(curUserID : String): Flow<List<Ticket>>
//
//    // get current user's bought tickets
//    // get all the tickets that the buyerId is current user, which means all tickets bought by this user
//    @Query("SELECT * FROM ticket_table WHERE buyerId = :curUserID")
//    suspend fun getBoughtTickets(curUserID : String): Flow<List<Ticket>>



}