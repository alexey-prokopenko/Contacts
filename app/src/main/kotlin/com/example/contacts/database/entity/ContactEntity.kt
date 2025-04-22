package com.example.contacts.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Запись в БД "Контакт"
 *
 * @property id идентификатор
 * @property lastName фамилия
 * @property firstName имя
 * @property middleName отчество
 */
@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "middle_name")
    val middleName: String?,
)
