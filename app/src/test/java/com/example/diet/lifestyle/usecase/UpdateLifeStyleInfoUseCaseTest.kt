package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class UpdateLifeStyleInfoUseCaseTest {
    lateinit var updateUseCase: UpdateLifeStyleInfoUseCase

    @MockK
    lateinit var repository: LifeStyleInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        updateUseCase = UpdateLifeStyleInfoUseCase(repository)
    }

    @Test
    fun testInvoke_whenUpdateDataSuccess() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = true

        coEvery { repository.update(dateString, lifeStyleInfo) } returns flowOf(Unit)

        runBlocking {
            kotlin.runCatching {
                updateUseCase(dateString, lifeStyleInfo).collect {
                    assertEquals(expected, it)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.update(dateString, lifeStyleInfo) }
    }


    @Test
    fun testInvoke_whenFailedWithDataNoExist_raiseDataNoExistException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = false

        coEvery { repository.update(dateString, lifeStyleInfo) } coAnswers {
            updateUseCase.occurDataNoExistException()
        }

        runBlocking {
            kotlin.runCatching {
                updateUseCase(dateString, lifeStyleInfo).collect {
                    assertEquals(expected, it)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.update(dateString, lifeStyleInfo) }
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

        val expected = false

        coEvery { repository.update(dateString, lifeStyleInfo) } coAnswers {
            updateUseCase.occurUnexpectedBehaviorException()
        }

        runBlocking {
            kotlin.runCatching {
                updateUseCase(dateString, lifeStyleInfo).collect {
                    assertEquals(expected, it)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.update(dateString, lifeStyleInfo) }
    }
}
