package com.example.contacts.domain.storage

import com.example.contacts.domain.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Хранилище контактов
 */
interface ContactsStorage {

    /**
     * Получить все контакты
     */
    suspend fun getAllContacts(): Flow<List<Contact>>

    /**
     * Сохранить контакт [contact]
     */
    suspend fun saveContact(contact: Contact)

    /**
     * Сохранить контакты [contacts]
     */
    suspend fun saveContacts(contacts: List<Contact>)
}
