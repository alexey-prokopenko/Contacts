package com.example.contacts.database.di

import android.content.Context
import androidx.room.Room
import com.example.contacts.database.ExampleDatabase
import com.example.contacts.database.dao.ContactsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Зависимости модуля базы данных
 */
@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {

    @Provides
    @Singleton
    fun provideExampleDatabase(@ApplicationContext appContext: Context): ExampleDatabase =
        Room
            .databaseBuilder(
                context = appContext,
                klass = ExampleDatabase::class.java,
                name = "example.db"
            )
            .createFromAsset("database/example.db")
            .build()

    @Provides
    fun provideContactsDao(exampleDatabase: ExampleDatabase): ContactsDao =
        exampleDatabase.contactsDao
}
