package com.example.contacts.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.contacts.database.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO для работы с контактами
 */
@Dao
interface ContactsDao {

    /**
     * Получить все записи контактов
     */
    @Query("SELECT * FROM contacts ORDER BY last_name ")
    fun getAllContacts(): Flow<List<ContactEntity>>

    /**
     * Вставить запись контакта [entity]
     */
    @Insert
    suspend fun insertContact(entity: ContactEntity)

    /**
     * Вставить список записей контактов
     */
    @Insert
    suspend fun insertContacts(entities: List<ContactEntity>)
}
