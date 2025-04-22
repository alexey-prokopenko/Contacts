package com.example.contacts.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contacts.database.dao.ContactsDao
import com.example.contacts.database.entity.ContactEntity

/**
 * Описание базы данных приложения
 */
@Database(
    entities = [
        ContactEntity::class,
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = [],
)
internal abstract class ExampleDatabase : RoomDatabase() {

    /**
     * DAO для работы с контактами
     */
    abstract val contactsDao: ContactsDao
}
