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

class SaveLifeStyleInfoUseCaseTest {
    lateinit var saveUseCase: SaveLifeStyleInfoUseCase

    @MockK
    lateinit var repository: LifeStyleInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        saveUseCase = SaveLifeStyleInfoUseCase(repository)
    }

    @Test
    fun testInvoke_whenSaveDataSuccess_returnTrue() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = true

        coEvery { repository.save(dateString, lifeStyleInfo) } returns flowOf(expected)

        runBlocking {
            saveUseCase(dateString, lifeStyleInfo, onSuccess = {
                assertEquals(it, expected)
            }, onFailed = {
                assertEquals(it, expected)
            }, onError = {
                assertEquals(it, expected)
            })
        }

        coVerify { repository.save(dateString, lifeStyleInfo) }
    }

    @Test
    fun testInvoke_whenSaveDataFail_returnFalse() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = false

        coEvery { repository.save(dateString, lifeStyleInfo) } returns flowOf(expected)

        runBlocking {
            saveUseCase(dateString, lifeStyleInfo, onSuccess = {
                assertEquals(it, expected)
            }, onFailed = {
                assertEquals(it, expected)
            }, onError = {
                assertEquals(it, expected)
            })
        }

        coVerify { repository.save(dateString, lifeStyleInfo) }
    }

    @Test
    fun testInvoke_whenOccursError_raiseException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)
        val exception = Exception()

        val expected = exception

        coEvery { repository.save(dateString, lifeStyleInfo) } throws expected

        runBlocking {
            saveUseCase(dateString, lifeStyleInfo, onSuccess = {
                assertEquals(it, expected)
            }, onFailed = {
                assertEquals(it, expected)
            }, onError = {
                assertEquals(it, expected)
            })
        }

        coVerify { repository.save(dateString, lifeStyleInfo) }
    }
}
