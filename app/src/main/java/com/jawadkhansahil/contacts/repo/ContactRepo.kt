package com.jawadkhansahil.contacts.repo

import androidx.lifecycle.LiveData
import com.jawadkhansahil.contacts.dao.ContactDao
import com.jawadkhansahil.contacts.model.Contact

class ContactRepo(val contactDao: ContactDao) {

    fun getContacts() : LiveData<List<Contact>>{
        return contactDao.getContacts()
    }

    suspend fun createContact(contact: Contact){
        contactDao.createContact(contact)
    }


    suspend fun updateContact(contact: Contact){
        contactDao.updateContact(contact)
    }

    suspend fun deleteContact(contact: Contact){
        contactDao.deleteContact(contact)
    }
}