package com.example.contacts.ui.main

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.R
import com.example.contacts.domain.usecase.AddContactUseCase
import com.example.contacts.domain.usecase.GetContactsUseCase
import com.example.contacts.domain.usecase.UpdateContactsUseCase
import com.example.contacts.ui.main.mapper.ContactsUiMapper
import com.example.contacts.ui.main.state.ContactState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Вью модель главного экрана
 */
@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    private val addContactUseCase: AddContactUseCase,
    private val updateContactsUseCase: UpdateContactsUseCase,
    private val contactsUiMapper: ContactsUiMapper,
    @ApplicationContext private val appContext: Context,
) : ViewModel() {

    private val _state by lazy { mutableStateOf(UiState()) }
    val state: UiState
        get() = _state.value

    private val _event = MutableSharedFlow<UiEvent>()
    val event = ::addEvent

    private val _effect = Channel<UiEffect>()
    val effect = _effect.receiveAsFlow()

    private var initDataJob: Job? = null

    init {
        subscribeEvents()
        if (initDataJob == null) initDataJob = initData()
    }

    /**
     * Инициализировать данные
     */
    private fun initData(): Job = viewModelScope.launch {
        getContactsUseCase().collect { contacts ->
            updateState { _state.value.copy(contactStates = contactsUiMapper.mapToUi(contacts)) }
        }
    }

    /**
     * Обновить состояние Ui, используя [reducer]
     */
    private fun updateState(reducer: UiState.() -> UiState) {
        val newState = state.reducer()
        _state.value = newState
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            _event.collect(::handleEvent)
        }
    }

    /**
     * Обработать событие от Ui [event]
     */
    fun handleEvent(event: UiEvent) {
        when (event) {
            is UiEvent.AddNewContact -> {
                sendEffect(UiEffect.AddNewContact())
            }

            is UiEvent.AddContact -> viewModelScope.launch {
                with(event) {
                    val isAdded = addContactUseCase(lastName, firstName, middleName)
                    if (isAdded) {
                        val message = appContext.getString(R.string.message_contact_added)
                        sendEffect(UiEffect.ShowSnack(message))
                    }
                }
            }

            is UiEvent.UpdateContacts -> viewModelScope.launch {
                sendEffect(UiEffect.Refreshing(isRefreshing = true))
                updateContactsUseCase()
                sendEffect(UiEffect.Refreshing(isRefreshing = false))
            }
        }
    }

    /**
     * Добавить событие от Ui [event]
     */
    private fun addEvent(event: UiEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    /**
     * Отправить эффект [effect] в Ui
     */
    private fun sendEffect(effect: UiEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }

    /**
     * Состояние Ui
     *
     * @property contactStates список контактов
     */
    data class UiState(
        val contactStates: List<ContactState> = emptyList(),
    )

    /**
     * События от Ui
     */
    sealed interface UiEvent {

        /**
         * Запрос на добавление нового контакта
         */
        data object AddNewContact : UiEvent

        /**
         * Добавить контакт
         *
         * @property lastName фамилия
         * @property firstName имя
         * @property middleName отчества
         */
        data class AddContact(
            val lastName: String,
            val firstName: String,
            val middleName: String,
        ) : UiEvent

        /**
         * Обновление списка контактов
         */
        data object UpdateContacts : UiEvent
    }

    sealed class UiEffect {

        /**
         * Добавить новый контакт
         */
        class AddNewContact : UiEffect()

        /**
         * Показать сообщение [message]
         */
        class ShowSnack(val message: String) : UiEffect()

        /**
         * Отобразить/скрыть состояние обновления [isRefreshing]
         */
        class Refreshing(val isRefreshing: Boolean) : UiEffect()
    }
}
