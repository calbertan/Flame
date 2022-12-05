package com.example.myapplication.Database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

// ticket table, save ticket information
// add foreign key, so that when delete a user in User table, all associated tickets will be deleted
@Entity(tableName = "ticket_table")
data class Ticket (
    // primary key ticket_id, use this to identify each ticket
    @PrimaryKey(autoGenerate = true)
    var ticket_id: Long = 0L,

    // expire date
    @ColumnInfo(name = "ticket_time_column")
    var time: String = "",

    @ColumnInfo(name = "ticket_location_column")
    var location: String = "",

    @ColumnInfo(name = "ticket_price_column")
    var price: String = "",

    @ColumnInfo(name = "ticket_description_column")
    var description: String = "",

    @ColumnInfo(name = "delivery_column")
    var delivery: String = "",

    // ticket status, 0 means not sold(also means belongs to seller), 1 means sold(also means belong to buyer)
    // default 0
    @ColumnInfo(name = "ticket_status_column")
    var status: Int = 0,

    // foreign key
    @ColumnInfo(name = "userId")
    var userId: Long = 0L,

    // buyerId and sellerId

    // for a ticket, the seller id is id of the ticket seller(as well as the one who
    // publish it), the buyer id is the id of the ticket buyer
    @ColumnInfo(name = "buyerId")
    var buyerId: Long = -1L,

    // here sellerId is same as userId
    // for a ticket the owner of the ticket is the one who publish it and sell it
    @ColumnInfo(name = "sellerId")
    var sellerId: Long = userId,
)