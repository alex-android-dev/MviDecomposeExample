package com.example.mvidecomposetest.presentation.contact_list_component

import com.example.mvidecomposetest.domain.Contact
import kotlinx.coroutines.flow.StateFlow

interface ContactListComponent {

    val model: StateFlow<Model>

    fun onContactClicked(contact: Contact)

    fun onAddContact()

    data class Model(val contactList: List<Contact>)
}