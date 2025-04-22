package com.example.contacts.ui.main.mapper

import com.example.contacts.domain.model.Contact
import com.example.contacts.domain.model.name
import com.example.contacts.ui.main.state.ContactState
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
internal class ContactsUiMapper @Inject constructor() {

    /**
     * Преобразовать список контактов [contacts] для Ui
     */
    fun mapToUi(contacts: List<Contact>): List<ContactState> =
        contacts.map { contact ->  contact.toUi() }

    private fun Contact.toUi(): ContactState =
        ContactState(
            model = this,
            lastName = this.lastName,
            name = this.name,
        )
}
