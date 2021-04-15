package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.usecase.dto.LifeStyleRequestDto
import com.example.diet.lifestyle.usecase.exception.NetworkFailureException
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
        val lifeStyle = LifeStyle(date, "Running", "2 hr", "1510 kcal")
        val lifeStyleRequestDto = LifeStyleRequestDto(
            lifeStyle.date,
            lifeStyle.name,
            lifeStyle.time,
            lifeStyle.burnedCalorie
        )

        val expected = lifeStyle
        coEvery {
            repository.createLifeStyle(
                lifeStyleRequestDto
            )
        } returns flowOf(expected)

        runBlocking {
            createUseCase(lifeStyleRequestDto)
                .catch { fail() }
                .collect {
                    assertEquals(expected, it)
                }
        }
    }

    @Test
    fun `사용자 입력과 동일한 활동 있음_활동 생성 실패`() {
        val date = DateTime.now()
        val lifeStyle = LifeStyle(date, "Running", "2 hr", "1510 kcal")
        val lifeStyleRequestDto = LifeStyleRequestDto(
            lifeStyle.date,
            lifeStyle.name,
            lifeStyle.time,
            lifeStyle.burnedCalorie
        )

        val expected = DataAlreadyExistException()
        coEvery {
            repository.createLifeStyle(
                lifeStyleRequestDto
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            createUseCase(lifeStyleRequestDto)
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
        val lifeStyle = LifeStyle(date, "Running", "2 hr", "1510 kcal")
        val lifeStyleRequestDto = LifeStyleRequestDto(
            lifeStyle.date,
            lifeStyle.name,
            lifeStyle.time,
            lifeStyle.burnedCalorie
        )

        val expected = NetworkFailureException()
        coEvery {
            repository.createLifeStyle(
                lifeStyleRequestDto
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            createUseCase(lifeStyleRequestDto)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }
}
