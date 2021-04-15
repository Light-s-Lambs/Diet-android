package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.usecase.exception.DataAlreadyExistException
import com.example.diet.lifestyle.usecase.exception.NetworkFailureException
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
class CreateLifeStyleUseCaseTest {
    lateinit var createUseCase: CreateLifeStyleUseCase

    @MockK
    lateinit var repository: LifeStyleRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        createUseCase = CreateLifeStyleUseCase(repository)
    }

    @Test
    fun `사용자의 입력과 동일한 활동 없음_활동 생성 성공`() {
        val date = DateTime.now()
        val lifeStyleRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val lifeStyle = LifeStyle(
            lifeStyleRequest.date,
            lifeStyleRequest.name,
            lifeStyleRequest.time,
            lifeStyleRequest.burnedCalorie
        )
        val expected = lifeStyle
        coEvery {
            repository.createLifeStyle(
                lifeStyleRequest
            )
        } returns flowOf(expected)

        runBlocking {
            createUseCase(lifeStyleRequest)
                .catch { fail() }
                .collect {
                    assertEquals(expected, it)
                }
        }
    }

    @Test
    fun `사용자 입력과 동일한 활동 있음_활동 생성 실패`() {
        val date = DateTime.now()
        val lifeStyleRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val expected = DataAlreadyExistException()
        coEvery {
            repository.createLifeStyle(
                lifeStyleRequest
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            createUseCase(lifeStyleRequest)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }

    @Test
    fun `네트워크 문제_활동 생성 실패`() {
        val date = DateTime.now()
        val lifeStyleRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val expected = NetworkFailureException()
        coEvery {
            repository.createLifeStyle(
                lifeStyleRequest
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            createUseCase(lifeStyleRequest)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }
}
