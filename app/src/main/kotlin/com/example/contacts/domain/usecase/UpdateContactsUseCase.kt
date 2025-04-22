package com.example.contacts.domain.usecase


/**
 * Кейс обновления списка контактов
 */
interface UpdateContactsUseCase {

    /**
     * Обновить список контактов
     */
    suspend operator fun invoke()
}