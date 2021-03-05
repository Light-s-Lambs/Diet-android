package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.FailedButFoundDataException
import com.example.diet.lifestyle.usecase.exception.NoMatchDataException
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
    fun testInvoke_whenSaveDataSuccess() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        coEvery { repository.save(dateString, lifeStyleInfo) } returns flowOf(Unit)

        runBlocking {
            kotlin.runCatching {
                saveUseCase(dateString, lifeStyleInfo)
            }
        }

        coVerify { repository.save(dateString, lifeStyleInfo) }
    }

    @Test
    fun testInvoke_whenSaveFailedButRepoHasData_raiseFailedButFoundDataException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = FailedButFoundDataException("Save Failed. But Found Data")

        coEvery { repository.save(dateString, lifeStyleInfo) } throws expected

        runBlocking {
            kotlin.runCatching {
                saveUseCase(dateString, lifeStyleInfo)
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

        coVerify { repository.save(dateString, lifeStyleInfo) }
    }

    @Test
    fun testInvoke_RepoHasNoMatchData_raiseNoMatchDataException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = NoMatchDataException("Save Failed. There is No Match Date")

        coEvery { repository.save(dateString, lifeStyleInfo) } throws expected

        runBlocking {
            kotlin.runCatching {
                saveUseCase(dateString, lifeStyleInfo)
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

        coVerify { repository.save(dateString, lifeStyleInfo) }
    }
}
