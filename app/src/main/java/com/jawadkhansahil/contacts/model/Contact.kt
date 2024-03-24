package com.jawadkhansahil.contacts.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val initial: String,
    val name: String,
    val phone: String
)