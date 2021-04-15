package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.usecase.exception.NetworkFailureException
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
class LoadLifeStyleInDayToListUseCaseTest {
    lateinit var loadInDayToListUseCase: LoadLifeStyleInDayToListUseCase

    @MockK
    lateinit var repository: LifeStyleRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadInDayToListUseCase = LoadLifeStyleInDayToListUseCase(repository)
    }

    @Test
    fun `해당 날짜에 저장된 활동들이 있음_해당 날짜의 활동 리스트 불러오기 성공`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", "22 hr", "348 kcal"),
            LifeStyle(date, "Running", "2 hr", "1510 kcal")
        )
        val expected = lifeStyleList
        coEvery {
            repository.loadLifeStyleInDayToList(date)
        } returns flowOf(expected)

        runBlocking {
            loadInDayToListUseCase(date)
                .catch { fail() }
                .collect {
                    assertEquals(expected, it)
                }
        }
    }

    @Test
    fun `해당 날짜에 저장된 활동이 없음_해당 날짜의 활동 리스트 불러오기 실패`() {
        val date = DateTime.now()
        val expected = DataNotFoundException()
        coEvery {
            repository.loadLifeStyleInDayToList(date)
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            loadInDayToListUseCase(date)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }

    @Test
    fun `네트워크 문제_해당 날짜의 활동 리스트 불러오기 실패`() {
        val date = DateTime.now()
        val expected = NetworkFailureException()
        coEvery {
            repository.loadLifeStyleInDayToList(date)
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            loadInDayToListUseCase(date)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }
}
