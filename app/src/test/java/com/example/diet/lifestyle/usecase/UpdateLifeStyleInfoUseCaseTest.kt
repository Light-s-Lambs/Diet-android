package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.DataNoExistException
import com.example.diet.lifestyle.usecase.exception.ConnectionErrorException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
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
    fun `생성된 객체 있음_갱신 성공`() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val basalMetabolism = 1900
        val activityMetabolism = 3758
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(basalMetabolism, activityMetabolism, lifeStyleList)
        val expected = lifeStyleInfo
        coEvery {
            repository.update(
                dateString,
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            )
        } returns flowOf(expected)

        runBlocking {
            updateUseCase(dateString, basalMetabolism, activityMetabolism, lifeStyleList)
                .catch { fail() }
                .collect {
                    assertEquals(expected, it)
                }
        }
    }

    @Test
    fun `생성된 객체 없음_갱신 실패`() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val basalMetabolism = 1900
        val activityMetabolism = 3758
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val expected = DataNoExistException("Data No Exist. Create Before Delete.")
        coEvery {
            repository.update(
                dateString,
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            updateUseCase(dateString, basalMetabolism, activityMetabolism, lifeStyleList)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }

    @Test
    fun `연결 문제_갱신 실패`() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val basalMetabolism = 1900
        val activityMetabolism = 3758
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val expected = ConnectionErrorException("No Connection")
        coEvery {
            repository.update(
                dateString,
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            updateUseCase(dateString, basalMetabolism, activityMetabolism, lifeStyleList)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }
}
