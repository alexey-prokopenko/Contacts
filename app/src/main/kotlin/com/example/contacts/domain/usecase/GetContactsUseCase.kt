package com.example.contacts.domain.usecase

import com.example.contacts.domain.model.Contact
import kotlinx.coroutines.flow.Flow

/**
 * Кейс получения списка контактов
 */
interface GetContactsUseCase {

    /**
     * Получить список контактов
     */
    suspend operator fun invoke(): Flow<List<Contact>>
}
