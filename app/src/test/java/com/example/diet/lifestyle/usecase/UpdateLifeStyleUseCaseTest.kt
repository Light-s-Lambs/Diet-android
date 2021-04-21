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
import kotlinx.coroutines.flow.*
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
    fun `기존에 저장된 정보로 만든 LifeStyleRequest 내용이 사용자가 입력한 정보로 만든 LifeStyleRequest 내용과 동일하지 않음_활동 갱신 성공`() {
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
    fun `기존에 저장된 정보로 만든 LifeStyleRequest 내용이 사용자가 입력한 정보로 만든 LifeStyleRequest 내용과 동일함_활동 갱신 실패`() {
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
    fun `기존에 저장된 정보로 만든 LifeStyleRequest 내용이 존재하지 않음_활동 갱신 실패`() {
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
    fun `기존에 저장된 정보로 만든 LifeStyleRequest와 사용자가 입력한 정보로 만든 LifeStyleRequest를 보낼때, 네트워크 문제로_3번 재시도_1회차 연결 성공_갱신 성공`() {
        val date = DateTime.now()
        val lifeStyleOriginRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val lifeStyleRevisionRequest = LifeStyleRequest(date, "Sleeping", 22.0, 348.0)
        val lifeStyleRevision = LifeStyle(
            lifeStyleRevisionRequest.date,
            lifeStyleRevisionRequest.name,
            lifeStyleRevisionRequest.time,
            lifeStyleRevisionRequest.burnedCalorie
        )
        var retryUntilZero = 1
        val maxRetries: Long = 3
        val exception = NetworkFailureException()
        val expected = lifeStyleRevision
        coEvery {
            repository.updateLifeStyle(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            )
        } returns callbackFlow {
            if (retryUntilZero == 0) {
                offer(expected)
                close()
            } else {
                retryUntilZero -= 1
                close(exception)
            }
        }

        runBlocking {
            updateUseCase(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
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
    fun `기존에 저장된 정보로 만든 LifeStyleRequest와 사용자가 입력한 정보로 만든 LifeStyleRequest를 보낼때, 네트워크 문제로_3번 재시도_3회차 연결 성공_갱신 성공`() {
        val date = DateTime.now()
        val lifeStyleOriginRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val lifeStyleRevisionRequest = LifeStyleRequest(date, "Sleeping", 22.0, 348.0)
        val lifeStyleRevision = LifeStyle(
            lifeStyleRevisionRequest.date,
            lifeStyleRevisionRequest.name,
            lifeStyleRevisionRequest.time,
            lifeStyleRevisionRequest.burnedCalorie
        )
        var retryUntilZero = 3
        val maxRetries: Long = 3
        val exception = NetworkFailureException()
        val expected = lifeStyleRevision
        coEvery {
            repository.updateLifeStyle(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            )
        } returns callbackFlow {
            if (retryUntilZero == 0) {
                offer(expected)
                close()
            } else {
                retryUntilZero -= 1
                close(exception)
            }
        }

        runBlocking {
            updateUseCase(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
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
    fun `기존에 저장된 정보로 만든 LifeStyleRequest와 사용자가 입력한 정보로 만든 LifeStyleRequest를 보낼때, 네트워크 문제로 3번 재시도_모든 재시도 연결 실패_갱신 실패`() {
        val date = DateTime.now()
        val lifeStyleOriginRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val lifeStyleRevisionRequest = LifeStyleRequest(date, "Sleeping", 22.0, 348.0)
        var retryUntilZero = 4
        val maxRetries: Long = 3
        val exception = NetworkFailureException()
        val expected = exception
        coEvery {
            repository.updateLifeStyle(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
            )
        } returns callbackFlow {
            if (retryUntilZero == 0) {
                close(exception)
            } else {
                retryUntilZero -= 1
                close(exception)
            }
        }

        runBlocking {
            updateUseCase(
                lifeStyleOriginRequest,
                lifeStyleRevisionRequest
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
