package com.example.contacts.domain.usecase

/**
 * Кейс добавления нового контакта
 */
interface AddContactUseCase {

    /**
     * Добавить новый контакт
     *
     * @param lastName фамилия
     * @param firstName имя
     * @param middleName отчество
     * @return true, если контакт добавлен успешно, иначе – false
     */
    suspend operator fun invoke(
        lastName: String,
        firstName: String,
        middleName: String,
    ): Boolean
}
