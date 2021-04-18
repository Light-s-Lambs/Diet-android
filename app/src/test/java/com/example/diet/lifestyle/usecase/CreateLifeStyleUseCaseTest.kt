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
    fun `사용자가 하단의 완료 버튼을 눌렀을때_사용자가 입력한 활동 정보가 저장된 활동 정보와 동일하지 않음_활동 생성 성공_생성한 활동 전달 후 화면 종료`() {
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
    fun `사용자가 하단의 완료 버튼을 눌렀을때_사용자가 입력한 활동 정보가 저장된 활동 정보와 동일함_활동 생성 실패_토스트로 에러 출력`() {
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
    fun `사용자가 하단의 완료 버튼을 눌렀을때_네트워크 문제로 50ms를 기다림을 3번 재시도_3회 실패_활동 생성 실패_토스트로 에러 출력`() {
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

    @Test
    fun `사용자가 하단의 완료 버튼을 눌렀을때_네트워크 문제로 50ms를 기다림을 3번 재시도_1회차 연결 성공_활동 생성 성공_생성한 활동 전달 후 화면종료`() {
    }

    @Test
    fun `사용자가 하단의 완료 버튼을 눌렀을때_네트워크 문제로 50ms를 기다림을 3번 재시도_3회차 연결 성공_활동 생성 성공_생성한 활동 전달 후 화면 종료`() {
    }
}
