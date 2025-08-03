package com.example.mvidecomposetest.presentation.contact_list_component

import android.annotation.SuppressLint
import com.example.mvidecomposetest.domain.Contact
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface ContactListComponent {

    val model: StateFlow<Model>

    fun onContactClicked(contact: Contact)

    fun onAddContact()

    @SuppressLint("UnsafeOptInUsageError")
    @Serializable
    data class Model(val contactList: List<Contact>)
}