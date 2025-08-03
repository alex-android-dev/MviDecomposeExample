package com.example.mvidecomposetest.presentation.contact_list_component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ComponentContextFactory
import com.arkivanov.essenty.backhandler.BackHandler
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.example.mvidecomposetest.core.componentScope
import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.GetContactsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * ComponentContext - класс для управления жизненным циклом компонента
 */

class DefaultContactListComponent(
    /**
     * Передаем component context, чтобы реализовать поля из интерфейса
     */
    componentContext: ComponentContext,
    val onEditingContactRequested: (Contact) -> Unit,
    val onAddContactRequested: () -> Unit,
) : ContactListComponent, ComponentContext by componentContext {

    /**
     * При первом создании экрана viewModel будет создана.
     * При последующих будет возвращаться созданный ранее экземпляр ViewModel
     */
    private val viewModel = instanceKeeper.getOrCreate { FakeViewModel() }

    init {
        /**
         * Управления жизненным циклом.
         * Отменяем корутину после уничтожения компонента
         * Мы это сделали в extension функции
         */
//        lifecycle.doOnDestroy { coroutineScope.cancel() }
    }

    private val repository = RepositoryImpl

    private val getContactsUseCase = GetContactsUseCase(repository)

    private val coroutineScope = componentScope()


    override val model: StateFlow<ContactListComponent.Model> = getContactsUseCase().map {
        ContactListComponent.Model(it)
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = ContactListComponent.Model(listOf())
    )


    override fun onContactClicked(contact: Contact) {
        onEditingContactRequested(contact)
    }

    override fun onAddContact() {
        onAddContactRequested()
    }

    companion object {
        private const val KEY = "DefaultContactListComponent"
    }

    /**
     * Поля из интерфейса. ComponentContext реализует эти методы.
     * Поскольку мы использовали делегат, то нам не нужна реализация этого полотна кода
     */

//    override val lifecycle: Lifecycle
//        get() = componentContext.lifecycle
//
//    override val stateKeeper: StateKeeper
//        get() = componentContext.stateKeeper
//
//    override val instanceKeeper: InstanceKeeper
//        get() = componentContext.instanceKeeper
//
//    override val backHandler: BackHandler
//        get() = componentContext.backHandler
//
//    override val componentContextFactory: ComponentContextFactory<ComponentContext>
//        get() = componentContext.componentContextFactory

}

/**
 * Для примера создаем ViewModel
 * Чтобы она не уничтожалась вместе с компонентом, мы используем данный интерфейс
 */
private class FakeViewModel() : InstanceKeeper.Instance {

    /**
     * При уничтожении можем переопределить метод onDestroy
     */
    override fun onDestroy() {
        super.onDestroy()
    }
}