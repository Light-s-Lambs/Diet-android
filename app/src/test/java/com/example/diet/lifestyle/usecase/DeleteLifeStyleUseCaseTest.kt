package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleRepository
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
    fun `삭제하고자 하는 활동과 동일한 활동이 있음_활동 삭제 성공`() {
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
            repository.delete(lifeStyleRequestDto)
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
    fun `삭제하고자 하는 활동과 동일한 활동이 없음_삭제 실패`() {
        val date = DateTime.now()
        val lifeStyle = LifeStyle(date, "Running", "2 hr", "1510 kcal")
        val lifeStyleRequestDto = LifeStyleRequestDto(
            lifeStyle.date,
            lifeStyle.name,
            lifeStyle.time,
            lifeStyle.burnedCalorie
        )

        val expected = DataNotFoundException("Data No Exist. Create Before Delete.")
        coEvery {
            repository.delete(lifeStyleRequestDto)
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
        val lifeStyle = LifeStyle(date, "Running", "2 hr", "1510 kcal")
        val lifeStyleRequestDto = LifeStyleRequestDto(
            lifeStyle.date,
            lifeStyle.name,
            lifeStyle.time,
            lifeStyle.burnedCalorie
        )
        val expected = ConnectionErrorException("No Connection")
        coEvery {
            repository.delete(lifeStyleRequestDto)
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
