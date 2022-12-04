package com.example.myapplication.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.Database.Entities.Ticket
import com.example.myapplication.Database.Entities.User


@Database(
    entities = [
        User::class,
        Ticket::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {

    abstract val userDatabaseDao: UserDatabaseDao

    companion object{
        //The Volatile keyword guarantees visibility of changes to the INSTANCE variable across threads
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context) : UserDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}