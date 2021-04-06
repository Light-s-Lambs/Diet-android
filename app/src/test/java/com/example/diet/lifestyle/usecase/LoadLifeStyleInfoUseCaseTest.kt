package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.ConnectionErrorException
import com.example.diet.lifestyle.usecase.exception.DataNotFoundException
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
    fun `객체 있음_불러오기 성공`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(date, lifeStyleList)
        val expected = lifeStyleInfo
        coEvery {
            repository.load(date)
        } returns flowOf(expected)

        runBlocking {
            loadUseCase(date)
                .catch { fail() }
                .collect {
                    assertEquals(expected, it)
                }
        }
    }

    @Test
    fun `객체 없음_불러오기 실패`() {
        val date = DateTime.now()
        val expected = DataNotFoundException("Data No Exist. Create Before Load.")
        coEvery {
            repository.load(date)
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            loadUseCase(date)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }

    @Test
    fun `연결 문제_불러오기 실패`() {
        val date = DateTime.now()
        val expected = ConnectionErrorException("No Connection")
        coEvery {
            repository.load(date)
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            loadUseCase(date)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }
}
