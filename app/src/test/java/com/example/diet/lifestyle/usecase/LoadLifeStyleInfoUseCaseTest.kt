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
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = lifeStyleInfo

        coEvery { repository.load(dateString) } returns flowOf(expected)

        runBlocking {
            loadUseCase(dateString, onSuccess = {
                assertEquals(it, expected)
            }, onFailed = {
                assertEquals(it, expected)
            }, onError = {
                assertEquals(it, expected)
            })
        }

        coVerify { repository.load(dateString) }
    }


    @Test
    fun testInvoke_whenRepoHasNoMatchData_returnsEmptyInfo() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val emptyLifeStyleInfo = LifeStyleInfo(0, 0, emptyList())

        val expected = emptyLifeStyleInfo

        coEvery { repository.load(dateString) } returns flowOf(expected)

        runBlocking {
            loadUseCase(dateString, onSuccess = {
                assertEquals(it, expected)
            }, onFailed = {
                assertEquals(it, expected)
            }, onError = {
                assertEquals(it, expected)
            })
        }

        coVerify { repository.load(dateString) }
    }

    @Test
    fun testInvoke_whenRepoOccursError_raiseException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val exception = Exception()

        val expected = exception

        coEvery { repository.load(dateString) } throws expected

        runBlocking {
            loadUseCase(dateString, onSuccess = {
                assertEquals(it, expected)
            }, onFailed = {
                assertEquals(it, expected)
            }, onError = {
                assertEquals(it, expected)
            })
        }

        coVerify { repository.load(dateString) }
    }
}
