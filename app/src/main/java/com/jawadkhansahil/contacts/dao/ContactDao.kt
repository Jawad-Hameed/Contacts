package com.jawadkhansahil.contacts.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jawadkhansahil.contacts.model.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getContacts() : LiveData<List<Contact>>

    @Insert
    suspend fun createContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

}