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
                loadUseCase(dateString).collect {
                    assertEquals(expected, it)
                }
            }.onFailure {
                Assert.fail()
            }
        }
        coVerify { repository.load(dateString) }
    }

    @Test
    fun testInvoke_whenRepoHasNoMatchData_raiseNoMatchDataException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = emptyList<LifeStyleInfo>()

        coEvery { repository.load(dateString) } coAnswers {
            loadUseCase.occurNoMatchDataException()
        }

        runBlocking {
            kotlin.runCatching {
                loadUseCase(dateString).collect {
                    assertEquals(expected, it.lifeStyleList)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.load(dateString) }
    }

    @Test
    fun testInvoke_whenUnexpectedErrorOccurs_raiseUnexpectedBehaviorException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = emptyList<LifeStyleInfo>()

        coEvery { repository.load(dateString) } coAnswers {
            loadUseCase.occurUnexpectedBehaviorException()
        }

        runBlocking {
            kotlin.runCatching {
                loadUseCase(dateString).collect {
                    assertEquals(expected, it.lifeStyleList)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.load(dateString) }
    }
}
