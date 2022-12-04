package com.example.myapplication.Database.Entities.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myapplication.Database.Entities.Ticket
import com.example.myapplication.Database.Entities.User

data class UserWithTickets(
    @Embedded val user : User,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "userId"
    )

    val tickets : List<Ticket>
)
