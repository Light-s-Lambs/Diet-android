package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.ConnectionErrorException
import com.example.diet.lifestyle.usecase.exception.DataAlreadyExistException
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
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
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
    fun `동일한 객체 없음_객체 생성 성공`() {
        val date = DateTime.now()
        val basalMetabolism = 1900
        val activityMetabolism = 3758
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(date, basalMetabolism, activityMetabolism, lifeStyleList)
        val expected = lifeStyleInfo
        coEvery {
            repository.create(
                date,
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            )
        } returns flowOf(expected)

        runBlocking {
            createUseCase(date, basalMetabolism, activityMetabolism, lifeStyleList)
                .catch { fail() }
                .collect {
                    assertEquals(expected, it)
                }
        }
    }

    @Test
    fun `동일한 객체 있음_객체 생성 실패`() {
        val date = DateTime.now()
        val basalMetabolism = 1900
        val activityMetabolism = 3758
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val expected = DataAlreadyExistException("Data Already Exist. Use Update Instead.")
        coEvery {
            repository.create(
                date,
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            createUseCase(date, basalMetabolism, activityMetabolism, lifeStyleList)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }

    @Test
    fun `연결 문제_객체 생성 실패`() {
        val date = DateTime.now()
        val basalMetabolism = 1900
        val activityMetabolism = 3758
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val expected = ConnectionErrorException("No Connection")
        coEvery {
            repository.create(
                date,
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            createUseCase(date, basalMetabolism, activityMetabolism, lifeStyleList)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }
}
