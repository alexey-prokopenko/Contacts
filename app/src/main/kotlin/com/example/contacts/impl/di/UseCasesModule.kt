package com.example.contacts.impl.di

import com.example.contacts.domain.usecase.AddContactUseCase
import com.example.contacts.domain.usecase.GetContactsUseCase
import com.example.contacts.domain.usecase.UpdateContactsUseCase
import com.example.contacts.impl.usecase.AddContactUseCaseImpl
import com.example.contacts.impl.usecase.GetContactsUseCaseImpl
import com.example.contacts.impl.usecase.UpdateContactsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Зависимости кейсов использования
 */
@Module
@InstallIn(ViewModelComponent::class)
internal interface UseCasesModule {

    @Binds
    @ViewModelScoped
    fun bindGetContactsUseCase(impl: GetContactsUseCaseImpl): GetContactsUseCase

    @Binds
    @ViewModelScoped
    fun bindAddContactUseCase(impl: AddContactUseCaseImpl): AddContactUseCase

    @Binds
    @ViewModelScoped
    fun bindUpdateContactsUseCaseImpl(impl: UpdateContactsUseCaseImpl): UpdateContactsUseCase
}
