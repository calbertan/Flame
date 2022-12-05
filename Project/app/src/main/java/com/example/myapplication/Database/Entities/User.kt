package com.example.myapplication.Database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// user table, save user information, has foreign key that link to ticket table
@Entity(tableName = "user_table")
data class User(
    // primary key userid, use this to identify each user
    @PrimaryKey(autoGenerate = true)
    var user_id: Long = 0L,

    @ColumnInfo(name = "user_name_column")
    var name: String = "",

    @ColumnInfo(name = "user_password_column")
    var password: String = "",

    @ColumnInfo(name = "user_email_column")
    var email: String = "",

    @ColumnInfo(name = "user_balance_column")
    var balance: Double = 10L,
)