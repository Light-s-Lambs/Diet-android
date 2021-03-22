package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class CreateLifeStyleInfoUseCaseTest {
    lateinit var createUseCase: CreateLifeStyleInfoUseCase

    @MockK
    lateinit var repository: LifeStyleInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        createUseCase = CreateLifeStyleInfoUseCase(repository)
    }

    @Test
    fun testInvoke_whenCreateDataSuccess() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = true

        coEvery { repository.create(dateString, lifeStyleInfo) } returns flowOf(Unit)

        runBlocking {
            kotlin.runCatching {
                createUseCase(dateString, lifeStyleInfo).collect {
                    assertEquals(expected, it)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.create(dateString, lifeStyleInfo) }
    }


    @Test
    fun testInvoke_whenCreateFailedWithDataExist_raiseDataAlreadyExistException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val exception = createUseCase.occurDataAlreadyExistException()
        val expected = false

        coEvery {
            repository.create(
                dateString,
                lifeStyleInfo
            )
        } throws exception

        runBlocking {
            kotlin.runCatching {
                createUseCase(dateString, lifeStyleInfo).collect {
                    assertEquals(expected, it)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.create(dateString, lifeStyleInfo) }
    }

    @Test
    fun testInvoke_whenUnexpectedErrorOccurs_raiseUnexpectedBehaviorException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val exception = createUseCase.occurUnexpectedBehaviorException()
        val expected = false

        coEvery { repository.create(dateString, lifeStyleInfo) } throws exception

        runBlocking {
            kotlin.runCatching {
                createUseCase(dateString, lifeStyleInfo).collect {
                    assertEquals(expected, it)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.create(dateString, lifeStyleInfo) }
    }
}
