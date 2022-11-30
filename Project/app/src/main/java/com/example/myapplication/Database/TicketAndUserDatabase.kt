package com.example.myapplication.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Ticket::class], version = 1)
abstract class TicketAndUserDatabase : RoomDatabase() { //XD: Room automatically generates implementations of your abstract UserDatabase class.
//    abstract val userDatabaseDao: UserDatabaseDao

    companion object{
        //The Volatile keyword guarantees visibility of changes to the INSTANCE variable across threads
        @Volatile
        private var INSTANCE: TicketAndUserDatabase? = null

        fun getInstance(context: Context) : TicketAndUserDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        TicketAndUserDatabase::class.java, "user_ticket_table").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}