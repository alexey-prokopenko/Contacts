package com.example.contacts.repository.di

import com.example.contacts.database.dao.ContactsDao
import com.example.contacts.domain.service.ContactsService
import com.example.contacts.domain.storage.ContactsStorage
import com.example.contacts.repository.service.StubContactsService
import com.example.contacts.repository.storage.RoomContactsStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlin.random.Random

/**
 * Зависимости модуля репозитория
 */
@Module
@InstallIn(SingletonComponent::class)
internal class RepositoryModule {

    @Provides
    fun provideRandom(): Random =
        Random(seed = System.currentTimeMillis())

    @Provides
    fun provideContactsService(random: Random): ContactsService =
        StubContactsService(
            random = random,
            dispatcher = Dispatchers.IO,
        )

    @Provides
    fun provideContactsStorage(contactsDao: ContactsDao): ContactsStorage =
        RoomContactsStorage(
            contactsDao = contactsDao,
            dispatcher = Dispatchers.IO,
        )
}
