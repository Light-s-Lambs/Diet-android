package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.usecase.dto.LifeStyleRequestDto
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
    fun `선택한 활동과 동일한 활동이 있음_활동 삭제 성공`() {
        val date = DateTime.now()
        val lifeStyle = LifeStyle(date, "Running", 2.0, 1510.0)
        val lifeStyleRequestDto = LifeStyleRequestDto(
            lifeStyle.date,
            lifeStyle.name,
            lifeStyle.time,
            lifeStyle.burnedCalorie
        )

        val expected = lifeStyle
        coEvery {
            repository.deleteLifeStyle(lifeStyleRequestDto)
        } returns flowOf(expected)

        runBlocking {
            deleteUseCase(lifeStyleRequestDto)
                .catch { fail() }
                .collect {
                    assertEquals(expected, it)
                }
        }
    }

    @Test
    fun `선택한 활동과 동일한 활동이 없음_활동 삭제 실패`() {
        val date = DateTime.now()
        val lifeStyle = LifeStyle(date, "Running", 2.0, 1510.0)
        val lifeStyleRequestDto = LifeStyleRequestDto(
            lifeStyle.date,
            lifeStyle.name,
            lifeStyle.time,
            lifeStyle.burnedCalorie
        )

        val expected = DataNotFoundException()
        coEvery {
            repository.deleteLifeStyle(lifeStyleRequestDto)
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            deleteUseCase(lifeStyleRequestDto)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }

    @Test
    fun `네트워크 문제_활동 삭제 실패`() {
        val date = DateTime.now()
        val lifeStyle = LifeStyle(date, "Running", 2.0, 1510.0)
        val lifeStyleRequestDto = LifeStyleRequestDto(
            lifeStyle.date,
            lifeStyle.name,
            lifeStyle.time,
            lifeStyle.burnedCalorie
        )
        val expected = NetworkFailureException()
        coEvery {
            repository.deleteLifeStyle(lifeStyleRequestDto)
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            deleteUseCase(lifeStyleRequestDto)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }
}
