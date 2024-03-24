package com.jawadkhansahil.contacts

import com.jawadkhansahil.contacts.model.Contact

interface AdapterListener {
    fun onclickContact(contact: Contact)
}