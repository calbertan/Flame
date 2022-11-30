package com.example.myapplication.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//XD: At compile time, Room automatically generates implementations of the custom DAO interface that you define.
// Click the middle mouse button (Windows users) to see the code generated automatically by the system.
@Dao
interface UserDatabaseDao {

    // the insert is use for the sign up part
    @Insert
    suspend fun insertUser(user: User)

    //A Flow is an async sequence of values
    //Flow produces values one at a time (instead of all at once) that can generate values
    //from async operations like network requests, database calls, or other async code.
    //It supports coroutines throughout its API, so you can transform a flow using coroutines as well!
    //Code inside the flow { ... } builder block can suspend. So the function is no longer marked with suspend modifier.
    //See more details here: https://kotlinlang.org/docs/flow.html#flows
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): Flow<List<User>>

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("DELETE FROM user_table WHERE user_id = :key") //":" indicates that it is a Bind variable
    suspend fun deleteUser(key: Long)


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
    suspend fun getPasswordByUserInputNameOrEmail(nameEmail: String) : String

    @Query("SELECT user_id FROM user_table WHERE user_name_column = :nameEmail OR user_email_column = :nameEmail")
    suspend fun getUserIdByUserInputNameOrEmail(nameEmail: String) : Long


}