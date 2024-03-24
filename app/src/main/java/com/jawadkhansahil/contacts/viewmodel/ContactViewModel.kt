package com.jawadkhansahil.contacts.viewmodel

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jawadkhansahil.contacts.model.Contact
import com.jawadkhansahil.contacts.repo.ContactRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(val contactRepo: ContactRepo) : ViewModel() {

    fun getContacts() : LiveData<List<Contact>>{
        return contactRepo.getContacts()
    }


    fun createContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
        contactRepo.createContact(contact)
        }
    }

    fun updateContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
        contactRepo.updateContact(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch(Dispatchers.IO) {
        contactRepo.deleteContact(contact)
        }
    }


    @SuppressLint("Range")
    fun fetchSimContacts(context: Context) {

        // Get TelephonyManager instance
        val telephonyManager =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        // Check if SIM card is available
        val contentResolver: ContentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null

        )
        cursor?.use {
            while (it.moveToNext()) {
                val name =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phoneNumber =
                    it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                createContact (Contact(0, name.elementAt(0).toString(), name, phoneNumber))
            }
        }
    }

}