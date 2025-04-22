package com.example.contacts.impl.usecase

import com.example.contacts.domain.model.Contact
import com.example.contacts.domain.storage.ContactsStorage
import com.example.contacts.domain.usecase.GetContactsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetContactsUseCaseImpl @Inject constructor(
    private val contactsStorage: ContactsStorage,
): GetContactsUseCase {

    override suspend fun invoke(): Flow<List<Contact>> =
         contactsStorage.getAllContacts()
}
