package com.example.contacts.repository.service

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random

internal class ContactsServiceTest {

    @Test
    fun getNewContactsTest() = runTest {
        val serviceTest = StubContactsService(
            random = Random(seed = System.currentTimeMillis()),
            dispatcher = StandardTestDispatcher(scheduler = testScheduler),
        )
        val contacts = serviceTest.getNewContacts()
        println("Generated contacts count: ${contacts.size}")
        contacts.forEach { contact ->
            println(contact)
        }
    }
}
