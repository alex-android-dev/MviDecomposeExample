package com.example.mvidecomposetest.presentation.add_contact_component

import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.AddContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultAddContactComponent() : AddContactComponent {

    private val repository = RepositoryImpl
    private val addContactUseCase = AddContactUseCase(repository)

    /**
     * Стейт экрана, на который мы подписываемся и рисуем новое состояние экрана
     */
    private val _model = MutableStateFlow(
        AddContactComponent.Model(
            userName = "",
            phoneNumber = ""
        )
    )

    override val model: StateFlow<AddContactComponent.Model>
        get() = _model.asStateFlow()

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
        addContactUseCase.invoke(username, phoneNumber)
    }
}