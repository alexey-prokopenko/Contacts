package com.example.contacts.domain.model

/**
 * Контакт
 *
 * @property id идентификатор
 * @property lastName фамилия
 * @property firstName имя
 * @property middleName отчество
 */
data class Contact(
    val id: String = "",
    val lastName: String = "",
    val firstName: String = "",
    val middleName: String? = null,
)

/**
 * Получить имя-отчество контакта
 */
val Contact.name: String
    get() = buildString {
        append(firstName); middleName?.let { append(" $it") }
    }
