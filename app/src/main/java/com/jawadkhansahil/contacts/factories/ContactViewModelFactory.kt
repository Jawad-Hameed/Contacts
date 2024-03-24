package com.jawadkhansahil.contacts.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jawadkhansahil.contacts.repo.ContactRepo
import com.jawadkhansahil.contacts.viewmodel.ContactViewModel

class ContactViewModelFactory(val contactRepo: ContactRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(contactRepo) as T
    }

}