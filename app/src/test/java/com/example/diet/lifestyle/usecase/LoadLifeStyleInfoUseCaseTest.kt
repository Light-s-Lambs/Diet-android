package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class LoadLifeStyleInfoUseCaseTest {
    lateinit var loadUseCase: LoadLifeStyleInfoUseCase

    @MockK
    lateinit var repository: LifeStyleInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadUseCase = LoadLifeStyleInfoUseCase(repository)
    }

    @Test
    fun testInvoke_whenRepoHasMatchData_returnMatchInfo() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = lifeStyleInfo

        coEvery { repository.load(dateString) } returns flowOf(lifeStyleInfo)

        runBlocking {
            kotlin.runCatching {
                loadUseCase(dateString, onSuccess = {
                    assertEquals(expected, it)
                })
            }
        }
        coVerify { repository.load(dateString) }
    }

    @Test
    fun testInvoke_whenLoadFailedButRepoHasData_raiseFailedButFoundDataException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = FailedButFoundDataException("Load Failed. But Found Data")

        coEvery { repository.load(dateString) } throws expected

        runBlocking {
            kotlin.runCatching {
                loadUseCase(dateString, onSuccess = {
                    Assert.fail()
                })
                Assert.fail()
            }.onFailure {
                when (it) {
                    is FailedButFoundDataException -> {
                        assertEquals(expected, it)
                        throw it
                    }
                }
            }
        }

        coVerify { repository.load(dateString) }
    }

    @Test
    fun testInvoke_whenRepoHasNoMatchData_raiseNoMatchDataException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = NoMatchDataException("Load Failed. There is No Match Data")

        coEvery { repository.load(dateString) } throws expected

        runBlocking {
            kotlin.runCatching {
                loadUseCase(dateString, onSuccess = {
                    Assert.fail()
                })
                Assert.fail()
            }.onFailure {
                when (it) {
                    is NoMatchDataException -> {
                        assertEquals(expected, it)
                        throw it
                    }
                }
            }
        }

        coVerify { repository.load(dateString) }
    }

    @Test
    fun testInvoke_whenUnexpectedErrorOccurs_raiseUnexpectedBehaviorException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = UnexpectedBehaviorException("Load Failed. Something weired happened.")

        coEvery { repository.load(dateString) } throws expected

        runBlocking {
            kotlin.runCatching {
                loadUseCase(dateString, onSuccess = {
                    Assert.fail()
                })
                Assert.fail()
            }.onFailure {
                when (it) {
                    is UnexpectedBehaviorException -> {
                        assertEquals(expected, it)
                        throw it
                    }
                }
            }
        }

        coVerify { repository.load(dateString) }
    }
}
