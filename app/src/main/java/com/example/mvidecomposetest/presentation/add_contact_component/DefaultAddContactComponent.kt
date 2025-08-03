package com.example.mvidecomposetest.presentation.add_contact_component

import com.arkivanov.decompose.ComponentContext
import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.AddContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

class DefaultAddContactComponent(
    componentContext: ComponentContext
) : AddContactComponent, ComponentContext by componentContext {

    private val repository = RepositoryImpl
    private val addContactUseCase = AddContactUseCase(repository)

    init {
        /**
         * Для сохранения состояния (после переворота экрана) мы подписываемся на stateKeeper
         * Нам нужно передать ключ по которому будет сохранена строка
         *
         * Внутрь передается функция, которая будет вызвана при смене конфигурации или смерти процесса
         * Мы пишем какие данные мы хотим сохранить
         */
        stateKeeper.register(KEY, AddContactComponent.Model.serializer()) {
            model.value
        }
    }

    /**
     * Стейт экрана, на который мы подписываемся и рисуем новое состояние экрана
     * Из stateKeeper мы достаем сохраненные данные по ключу. Если этих данных нет, то возвращается null.
     * При возврате null мы используем значение по умолчанию
     */
    private val _model = MutableStateFlow(
        stateKeeper.consume(KEY, AddContactComponent.Model.serializer())
            ?: AddContactComponent.Model(
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


    companion object {
        private const val KEY = "DefaultAddContactComponent"
    }
}