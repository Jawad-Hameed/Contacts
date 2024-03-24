package com.jawadkhansahil.contacts

import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jawadkhansahil.contacts.model.Contact
import com.jawadkhansahil.contacts.viewmodel.ContactViewModel

class BottomSheet(val context: Context, val contact: Contact) {
    fun createBottomSheet(contactViewModel: ContactViewModel){
        val bottomSheetDialog = BottomSheetDialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet, null)
        bottomSheetDialog.setContentView(view)
        val contactName = view.findViewById<EditText>(R.id.contactName)
        val phoneNumber = view.findViewById<EditText>(R.id.phoneNumber)
        val saveContact = view.findViewById<AppCompatButton>(R.id.saveContact)
        contactName.setText(contact.name)
        phoneNumber.setText(contact.phone)
        bottomSheetDialog.show()
        saveContact?.setOnClickListener {
            if (contactName?.text.toString().isNotEmpty()){
                if (phoneNumber?.text.toString().isNotEmpty()){
                    val initial = contactName?.text?.elementAt(0).toString()
                    val contact = Contact(contact.id, initial, contactName?.text.toString(), phoneNumber?.text.toString())
                    if (contact.id == 0){
                        contactViewModel.createContact(contact)
                    }else{
                        contactViewModel.updateContact(contact)
                    }

                    bottomSheetDialog.dismiss()
                    Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter phone number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Please enter contact name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
