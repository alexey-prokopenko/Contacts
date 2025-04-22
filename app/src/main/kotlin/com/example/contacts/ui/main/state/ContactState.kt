package com.example.contacts.ui.main.state

import androidx.compose.runtime.Stable
import com.example.contacts.domain.model.Contact

/**
 * Ui модель для отображения контакта
 *
 * @property model модель данных
 * @property lastName фамилия
 * @property name имя
 */
@Stable
internal class ContactState(
    val model: Contact,
    val lastName: String,
    val name: String,
)
