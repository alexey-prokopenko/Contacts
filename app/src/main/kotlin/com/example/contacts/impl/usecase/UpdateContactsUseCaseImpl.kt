package com.example.contacts.impl.usecase

import com.example.contacts.domain.service.ContactsService
import com.example.contacts.domain.storage.ContactsStorage
import com.example.contacts.domain.usecase.UpdateContactsUseCase
import javax.inject.Inject

internal class UpdateContactsUseCaseImpl @Inject constructor(
    private val contactsService: ContactsService,
    private val contactsStorage: ContactsStorage
) : UpdateContactsUseCase{

    override suspend fun invoke() {
        val newContacts = contactsService.getNewContacts()
        contactsStorage.saveContacts(newContacts)
    }
}