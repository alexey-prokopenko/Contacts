package com.example.contacts.impl.usecase

import com.example.contacts.domain.model.Contact
import com.example.contacts.domain.storage.ContactsStorage
import com.example.contacts.domain.usecase.AddContactUseCase
import java.util.UUID
import javax.inject.Inject

internal class AddContactUseCaseImpl @Inject constructor(
    private val contactsStorage: ContactsStorage,
) : AddContactUseCase {

    override suspend fun invoke(lastName: String, firstName: String, middleName: String): Boolean =
        runCatching {
            val contact = Contact(
                id = UUID.randomUUID().toString(),
                lastName = lastName.trim(),
                firstName = firstName.trim(),
                middleName = middleName.trim().takeIf { it.isNotEmpty() },
            )
            contactsStorage.saveContact(contact)
            true
        }.getOrDefault(defaultValue = false)
}
