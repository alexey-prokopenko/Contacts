package com.example.contacts.repository.service

import com.example.contacts.domain.model.Contact
import com.example.contacts.domain.service.ContactsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

/**
 * Сервис контактов генерирует имена случайным образом из заданного списка
 */
internal class StubContactsService @Inject constructor(
    private val random: Random,
    private val dispatcher: CoroutineDispatcher,
) : ContactsService {

    private val lastNames = listOf(
        "Иванов",
        "Петров",
        "Сидоров",
        "Кузнецов",
        "Смирнов",
        "Попов",
        "Васильев",
        "Соколов",
        "Михайлов",
        "Морозов",
    )

    private val firstNames = listOf(
        "Александр",
        "Михаил",
        "Иван",
        "Максим",
        "Артём",
        "Даниил",
        "Дмитрий",
        "Николай",
        "Андрей",
        "Егор",
    )

    private val middleNames = listOf(
        "Сергеевич",
        "Борисович",
        "Михайлович",
        "Петрович",
        "Семёнович",
        "Константинович",
        "Владимирович",
        "Леонидович",
        "Дмитриевич",
        "Тимофеевич",
    )

    override suspend fun getNewContacts(): List<Contact> =
        withContext(dispatcher) {
            delay(LOADING_DELAY)

            List(NEW_COUNT_NAMES) {
                Contact(
                    id = UUID.randomUUID().toString(),
                    lastName = lastNames.getRandom(),
                    firstName = firstNames.getRandom(),
                    middleName = middleNames.getRandom(),
                )
            }
        }

    /**
     * Получить случайные элементы из коллекции
     */
    private fun <T> List<T>.getRandom(): T =
        this.get(index = random.nextInt(0, this.size))

    private companion object {

        /**
         * Количество новых имен
         */
        const val NEW_COUNT_NAMES = 3

        /**
         * Задержка в мс для имитации времени загрузки
         */
        const val LOADING_DELAY = 2000L
    }
}
