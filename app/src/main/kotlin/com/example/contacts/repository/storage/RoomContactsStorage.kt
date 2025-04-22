package com.example.contacts.repository.storage

import com.example.contacts.database.dao.ContactsDao
import com.example.contacts.database.entity.ContactEntity
import com.example.contacts.domain.model.Contact
import com.example.contacts.domain.storage.ContactsStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.map

internal class RoomContactsStorage @Inject constructor(
    private val contactsDao: ContactsDao,
    private val dispatcher: CoroutineDispatcher,
) : ContactsStorage {

    override suspend fun getAllContacts(): Flow<List<Contact>> =
        withContext(dispatcher) {
            contactsDao.getAllContacts()
                .map { entities ->
                entities.map { entity ->
                    Contact(
                        id = entity.id,
                        lastName = entity.lastName,
                        firstName = entity.firstName,
                        middleName = entity.middleName,
                    )
                }
            }
        }

    override suspend fun saveContact(contact: Contact) =
        withContext(dispatcher) {
            ContactEntity(
                id = contact.id,
                lastName = contact.lastName,
                firstName = contact.firstName,
                middleName = contact.middleName,
            ).let { contactsDao.insertContact(it) }
        }

    override suspend fun saveContacts(contacts: List<Contact>) =
        withContext(dispatcher) {
            contacts.map { contact ->
                ContactEntity(
                    id = contact.id,
                    lastName = contact.lastName,
                    firstName = contact.firstName,
                    middleName = contact.middleName,
                )
            }.let { entities ->
                contactsDao.insertContacts(entities)
            }
        }
}
