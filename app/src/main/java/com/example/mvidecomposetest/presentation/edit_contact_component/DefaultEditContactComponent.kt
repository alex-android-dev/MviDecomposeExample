package com.example.mvidecomposetest.presentation.edit_contact_component

import com.arkivanov.decompose.ComponentContext
import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.EditContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultEditContactComponent(
    componentContext: ComponentContext,
    private val contact: Contact
) : EditContactComponent,
    ComponentContext by componentContext {

    val repository = RepositoryImpl
    val editContactUseCase = EditContactUseCase(repository)

    private val _model = MutableStateFlow(
        stateKeeper.consume(
            KEY,
            EditContactComponent.Model.serializer()
        ) ?: EditContactComponent.Model(
            userName = contact.username,
            phoneNumber = contact.phone
        )
    )

    /**
     * Стейт экрана, на который мы подписываемся и рисуем новое состояние экрана
     */
    override val model: StateFlow<EditContactComponent.Model>
        get() = _model.asStateFlow()

    init {
        stateKeeper.register(KEY, EditContactComponent.Model.serializer()) {
            model.value
        }
    }

    /**
     * Методы, которые мы вызываем на экране
     */
    override fun onUserNameChanged(userName: String) {
        _model.value = model.value.copy(userName = userName)
    }

    override fun onNumberPhoneChanged(phoneNumber: String) {
        _model.value = model.value.copy(phoneNumber = phoneNumber)
    }

    override fun onSavedContactClicked() {
        val (username, phoneNumber) = model.value
        editContactUseCase.invoke(contact.copy(username = username, phone = phoneNumber))
    }

    companion object {
        private const val KEY = "DefaultEditContactComponent"
    }
}