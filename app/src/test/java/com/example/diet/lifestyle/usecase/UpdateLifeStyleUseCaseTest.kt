package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.usecase.exception.DataNotFoundException
import com.example.diet.lifestyle.usecase.exception.IdenticalDataException
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
class UpdateLifeStyleUseCaseTest {
    lateinit var updateUseCase: UpdateLifeStyleUseCase

    @MockK
    lateinit var repository: LifeStyleRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        updateUseCase = UpdateLifeStyleUseCase(repository)
    }

    @Test
    fun `기존 활동에 변경 사항이 있음_활동 갱신 성공`() {
        val date = DateTime.now()
        val lifeStyleOriginRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val lifeStyleRevisionRequest = LifeStyleRequest(date, "Sleeping", 22.0, 348.0)
        val lifeStyleRevision = LifeStyle(
            lifeStyleRevisionRequest.date,
            lifeStyleRevisionRequest.name,
            lifeStyleRevisionRequest.time,
            lifeStyleRevisionRequest.burnedCalorie
        )
        val expected = lifeStyleRevision
        coEvery {
            repository.updateLifeStyle(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            )
        } returns flowOf(expected)

        runBlocking {
            updateUseCase(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            ).catch {
                fail()
            }.collect {
                assertEquals(expected, it)
            }
        }
    }

    @Test
    fun `기존 활동에 변경 사항이 없음_활동 갱신 실패`() {
        val date = DateTime.now()
        val lifeStyleOriginRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val lifeStyleRevisionRequest = LifeStyleRequest(date, "Sleeping", 22.0, 348.0)
        val expected = IdenticalDataException()
        coEvery {
            repository.updateLifeStyle(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            updateUseCase(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            ).catch {
                assertEquals(expected::class, it::class)
            }.collect {
                fail()
            }
        }
    }

    @Test
    fun `기존 활동이 없음_활동 갱신 실패`() {
        val date = DateTime.now()
        val lifeStyleOriginRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val lifeStyleRevisionRequest = LifeStyleRequest(date, "Sleeping", 22.0, 348.0)
        val expected = DataNotFoundException()
        coEvery {
            repository.updateLifeStyle(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            updateUseCase(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            ).catch {
                assertEquals(expected::class, it::class)
            }.collect {
                fail()
            }
        }
    }

    @Test
    fun `네트워크 문제_활동 갱신 실패`() {
        val date = DateTime.now()
        val lifeStyleOriginRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val lifeStyleRevisionRequest = LifeStyleRequest(date, "Sleeping", 22.0, 348.0)
        val expected = NetworkFailureException()
        coEvery {
            repository.updateLifeStyle(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            updateUseCase(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            ).catch {
                assertEquals(expected::class, it::class)
            }.collect {
                fail()
            }
        }
    }
}
