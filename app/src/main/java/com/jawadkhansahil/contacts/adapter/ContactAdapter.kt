package com.jawadkhansahil.contacts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.jawadkhansahil.contacts.AdapterListener
import com.jawadkhansahil.contacts.R
import com.jawadkhansahil.contacts.model.Contact

class ContactAdapter(
    var contacts: List<Contact>,
    val context: Context,
    val adapterListener: AdapterListener
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun getContact(position: Int): Contact{
        return contacts[position]
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var initial: TextView = itemView.findViewById(R.id.initial)
        var contactName: TextView = itemView.findViewById(R.id.contactName)
        var contactItem: ConstraintLayout = itemView.findViewById(R.id.contactItem)
        fun bind(contact: Contact) {
            initial.text = contact.initial
            contactName.text = contact.name

            contactItem.setOnClickListener {
                adapterListener.onclickContact(contact)
            }
        }
    }
}
