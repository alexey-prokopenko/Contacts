package com.example.contacts.domain.service

import com.example.contacts.domain.model.Contact

/**
 * Сервис контактов
 */
interface ContactsService {

    /**
     * Получить новые контакты
     */
    suspend fun getNewContacts(): List<Contact>
}
