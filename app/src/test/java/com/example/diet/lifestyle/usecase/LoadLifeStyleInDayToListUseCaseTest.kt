package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.usecase.exception.DataNotFoundException
import com.example.diet.lifestyle.usecase.exception.NetworkFailureException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
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
    fun `지정된 날짜에 저장된 기존 활동들이 있음_해당 날짜의 활동 리스트 불러오기 성공`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        val expected = lifeStyleList
        coEvery {
            repository.loadLifeStyleInDayToList(
                date
            )
        } returns flowOf(expected)

        runBlocking {
            loadInDayToListUseCase(
                date
            ).catch {
                fail()
            }.collect {
                assertEquals(expected, it)
            }
        }
    }

    @Test
    fun `지정된 날짜에 저장된 기존 활동이 없음_해당 날짜의 활동 리스트 불러오기 실패`() {
        val date = DateTime.now()
        val expected = DataNotFoundException()
        coEvery {
            repository.loadLifeStyleInDayToList(
                date
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            loadInDayToListUseCase(
                date
            ).catch {
                assertEquals(expected::class, it::class)
            }.collect {
                fail()
            }
        }
    }

    @Test
    fun `지정된 날짜를 보낼때, 네트워크 문제로 3번 재시도_1회차 연결 성공_해당 날짜의 활동 리스트 불러오기 성공`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        var retryUntilZero = 1
        val maxRetries: Long = 3
        val exception = NetworkFailureException()
        val expected = lifeStyleList
        coEvery {
            repository.loadLifeStyleInDayToList(date)
        } returns callbackFlow {
            if (retryUntilZero == 0) {
                offer(expected)
                close()
            } else {
                retryUntilZero--
                close(exception)
            }
        }

        runBlocking {
            loadInDayToListUseCase(
                date
            ).retry(
                maxRetries
            ).catch {
                fail()
            }.collect {
                assertEquals(expected, it)
            }
        }
    }

    @Test
    fun `지정된 날짜를 보낼때, 네트워크 문제로 3번 재시도_3회차 연결 성공_해당 날짜의 활동 리스트 불러오기 성공`() {
        val date = DateTime.now()
        val lifeStyleList = listOf(
            LifeStyle(date, "Sleeping", 22.0, 348.0),
            LifeStyle(date, "Running", 2.0, 1510.0)
        )
        var retryUntilZero = 3
        val maxRetries: Long = 3
        val exception = NetworkFailureException()
        val expected = lifeStyleList
        coEvery {
            repository.loadLifeStyleInDayToList(date)
        } returns callbackFlow {
            if (retryUntilZero == 0) {
                offer(expected)
                close()
            } else {
                retryUntilZero--
                close(exception)
            }
        }

        runBlocking {
            loadInDayToListUseCase(
                date
            ).retry(
                maxRetries
            ).catch {
                fail()
            }.collect {
                assertEquals(expected, it)
            }
        }
    }

    @Test
    fun `지정된 날짜를 보낼때, 네트워크 문제로 3번 재시도_모든 재시도 연결 실패_해당 날짜의 활동 리스트 불러오기 실패`() {
        val date = DateTime.now()
        var retryUntilZero = 4
        val maxRetries: Long = 3
        val exception = NetworkFailureException()
        val expected = exception
        coEvery {
            repository.loadLifeStyleInDayToList(date)
        } returns callbackFlow {
            if (retryUntilZero == 0) {
                close(exception)
            } else {
                retryUntilZero--
                close(exception)
            }
        }

        runBlocking {
            loadInDayToListUseCase(
                date
            ).retry(
                maxRetries
            ).catch {
                assertEquals(expected::class, it::class)
            }.collect {
                fail()
            }
        }
    }
}
