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
class DeleteLifeStyleUseCaseTest {
    lateinit var deleteUseCase: DeleteLifeStyleUseCase

    @MockK
    lateinit var repository: LifeStyleRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deleteUseCase = DeleteLifeStyleUseCase(repository)
    }

    @Test
    fun `사용자가 입력한 정보로 만든 LifeStyleRequest 내용이 저장된 활동 정보와 동일함_활동 삭제 성공`() {
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
            repository.deleteLifeStyle(
                lifeStyleRequest
            )
        } returns flowOf(expected)

        runBlocking {
            deleteUseCase(
                lifeStyleRequest
            ).catch {
                fail()
            }.collect {
                assertEquals(expected, it)
            }
        }
    }

    @Test
    fun `사용자가 입력한 정보로 만든 LifeStyleRequest 내용이 저장된 활동 정보와 동일하지 않음_활동 삭제 실패`() {
        val date = DateTime.now()
        val lifeStyleRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val expected = DataNotFoundException()
        coEvery {
            repository.deleteLifeStyle(lifeStyleRequest)
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            deleteUseCase(
                lifeStyleRequest
            ).catch {
                assertEquals(expected::class, it::class)
            }.collect {
                fail()
            }
        }
    }

    @Test
    fun `사용자가 입력한 정보로 만든 LifeStyleRequest를 보낼 때, 네트워크 문제로 3번 재시도_1회차 연결 성공_활동 삭제 성공`() {
        val date = DateTime.now()
        val lifeStyleRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val lifeStyle = LifeStyle(
            lifeStyleRequest.date,
            lifeStyleRequest.name,
            lifeStyleRequest.time,
            lifeStyleRequest.burnedCalorie
        )
        var retryUntilZero = 1
        val maxRetries: Long = 3
        val exception = NetworkFailureException()
        val expected = lifeStyle
        coEvery {
            repository.deleteLifeStyle(
                lifeStyleRequest
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
            deleteUseCase(
                lifeStyleRequest
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
    fun `사용자가 입력한 정보로 만든 LifeStyleRequest를 보낼 때, 네트워크 문제로 3번 재시도_3회차 연결 성공_활동 삭제 성공`() {
        val date = DateTime.now()
        val lifeStyleRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        val lifeStyle = LifeStyle(
            lifeStyleRequest.date,
            lifeStyleRequest.name,
            lifeStyleRequest.time,
            lifeStyleRequest.burnedCalorie
        )
        var retryUntilZero = 3
        val maxRetries: Long = 3
        val exception = NetworkFailureException()
        val expected = lifeStyle
        coEvery {
            repository.deleteLifeStyle(
                lifeStyleRequest
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
            deleteUseCase(
                lifeStyleRequest
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
    fun `사용자가 입력한 정보로 만든 LifeStyleRequest를 보낼 때, 네트워크 문제로 3번 재시도_모든 재시도 연결 실패_활동 삭제 실패`() {
        val date = DateTime.now()
        val lifeStyleRequest = LifeStyleRequest(date, "Running", 2.0, 1510.0)
        var retryUntilZero = 4
        val maxRetries: Long = 3
        val exception = NetworkFailureException()
        val expected = exception
        coEvery {
            repository.deleteLifeStyle(
                lifeStyleRequest
            )
        } returns callbackFlow {
            if (retryUntilZero == 0) {
                close()
            } else {
                retryUntilZero -= 1
                close(exception)
            }
        }

        runBlocking {
            deleteUseCase(
                lifeStyleRequest
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
