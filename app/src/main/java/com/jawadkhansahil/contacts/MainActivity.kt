package com.jawadkhansahil.contacts

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jawadkhansahil.contacts.adapter.ContactAdapter
import com.jawadkhansahil.contacts.database.ContactDatabase
import com.jawadkhansahil.contacts.databinding.ActivityMainBinding
import com.jawadkhansahil.contacts.factories.ContactViewModelFactory
import com.jawadkhansahil.contacts.model.Contact
import com.jawadkhansahil.contacts.repo.ContactRepo
import com.jawadkhansahil.contacts.viewmodel.ContactViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class MainActivity : AppCompatActivity(), AdapterListener {
    lateinit var contactViewModel: ContactViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var contactAdapter: ContactAdapter
    var list: ArrayList<Contact>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contactDao = ContactDatabase.getContactDatabase(application).contactDao()
        val repo = ContactRepo(contactDao)
        contactViewModel = ViewModelProvider(this, ContactViewModelFactory(repo)).get(
            ContactViewModel::class.java)

        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            if (sharedPreferences.getString("isSaved", "NO") == "YES"){

            }else{
                editor.putString("isSaved", "YES")
                editor.apply()
                contactViewModel.fetchSimContacts(this)
            }

        }else{
            Toast.makeText(this, "Please grant contact permission", Toast.LENGTH_SHORT).show()
        }



        contactAdapter = ContactAdapter(emptyList(), this, this)
        binding.recyclerView.adapter = contactAdapter
        val itemTouchHelper = ItemTouchHelper(simpleCallBack)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        contactViewModel.getContacts().observe(this, Observer {
            list = it as ArrayList<Contact>?
            contactAdapter.contacts = list!!
            contactAdapter.notifyDataSetChanged()
        })

        binding.addContact.setOnClickListener {
            val contact = Contact(0, "", "", "")
            val bottomSheetDialog = BottomSheet(this, contact)
            bottomSheetDialog.createBottomSheet(contactViewModel)

        }
    }

    val simpleCallBack: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val contact = contactAdapter.getContact(position)

            when (direction) {
                ItemTouchHelper.LEFT -> {
                    contactViewModel.deleteContact(contact)
                }
                ItemTouchHelper.RIGHT -> {
                    val swipeThreshold = viewHolder.itemView.width / 5 // Half of item width
                    val dX = viewHolder.itemView.translationX
                    if (Math.abs(dX) <= swipeThreshold) {
                        // Swipe distance is within the threshold, reset translation and notify adapter
                        viewHolder.itemView.translationX = 0f
                        contactAdapter.notifyItemChanged(position)
                    } else {
                        // Swipe distance exceeds the threshold, show bottom sheet
                        viewHolder.itemView.translationX = 0f
                        contactAdapter.notifyItemChanged(position)
                        val bottomSheetDialog = BottomSheet(this@MainActivity, contact)
                        bottomSheetDialog.createBottomSheet(contactViewModel)
                    }
                }
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

            RecyclerViewSwipeDecorator.Builder(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.red))
                .addSwipeLeftActionIcon(R.drawable.delete) // You can add an icon for left swipe if needed
                .addSwipeRightBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.orange))
                .addSwipeRightActionIcon(R.drawable.edit) // You can add an icon for right swipe if needed
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onclickContact(contact: Contact) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.phone))
            startActivity(intent)

        }else{
            Toast.makeText(this, "Please grant phone permission", Toast.LENGTH_SHORT).show()
        }
    }
}

