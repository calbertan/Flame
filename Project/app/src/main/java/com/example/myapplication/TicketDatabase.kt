package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ticket::class], version = 1)
abstract class TicketDatabase : RoomDatabase() { //XD: Room automatically generates implementations of your abstract TicketDatabase class.
    abstract val ticketDatabaseDao: TicketDatabaseDao

    companion object{
        //The Volatile keyword guarantees visibility of changes to the INSTANCE variable across threads
        @Volatile
        private var INSTANCE: TicketDatabase? = null

        fun getInstance(context: Context) : TicketDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        TicketDatabase::class.java, "ticket_table").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}