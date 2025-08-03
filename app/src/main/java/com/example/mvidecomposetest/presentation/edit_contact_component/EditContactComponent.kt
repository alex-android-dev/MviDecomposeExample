package com.example.mvidecomposetest.presentation.edit_contact_component

import android.annotation.SuppressLint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface EditContactComponent {

    /**
     * Стейт экрана, на который мы подписываемся и рисуем новое состояние экрана
     */
    val model: StateFlow<Model>

    /**
     * Методы, которые мы вызываем на экране
     */

    fun onUserNameChanged(userName: String)

    fun onNumberPhoneChanged(phoneNumber: String)

    fun onSavedContactClicked()


    /**
     * Класс, который содержит все поля, которые будут меняться на экране
     */
    @SuppressLint("UnsafeOptInUsageError")
    @Serializable
    data class Model(
        val userName: String,
        val phoneNumber: String,
    )
}