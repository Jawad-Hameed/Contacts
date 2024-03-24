package com.jawadkhansahil.contacts.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jawadkhansahil.contacts.model.Contact
import com.jawadkhansahil.contacts.dao.ContactDao

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        private var INSTANCE: ContactDatabase? = null

        fun getContactDatabase(context: Context): ContactDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ContactDatabase::class.java,
                        "contacts_database"
                    ).build()
                }
            }

            return INSTANCE!!
        }
    }
}