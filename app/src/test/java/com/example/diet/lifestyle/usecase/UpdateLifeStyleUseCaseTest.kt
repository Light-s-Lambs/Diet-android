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
    fun `변경하고자 하는 활동과 동일한 활동이 있음_활동 갱신 성공`() {
        val date = DateTime.now()
        val lifeStyleOrigin = LifeStyle(date, "Running", "2 hr", "1510 kcal")
        val lifeStyleRevision = LifeStyle(date, "Sleeping", "22 hr", "348 kcal")

        val lifeStyleOriginRequestDto = LifeStyleRequestDto(
            lifeStyleOrigin.date,
            lifeStyleOrigin.name,
            lifeStyleOrigin.time,
            lifeStyleOrigin.burnedCalorie
        )
        val lifeStyleRevisionRequestDto = LifeStyleRequestDto(
            lifeStyleRevision.date,
            lifeStyleRevision.name,
            lifeStyleRevision.time,
            lifeStyleRevision.burnedCalorie
        )

        val expected = lifeStyleRevision
        coEvery {
            repository.update(
                lifeStyleOriginRequestDto,
                lifeStyleRevisionRequestDto
            )
        } returns flowOf(expected)

        runBlocking {
            updateUseCase(
                lifeStyleOriginRequestDto,
                lifeStyleRevisionRequestDto
            )
                .catch { fail() }
                .collect {
                    assertEquals(expected, it)
                }
        }
    }

    @Test
    fun `변경하고자 하는 활동과 동일한 활동이 없음_활동 갱신 실패`() {
        val date = DateTime.now()
        val lifeStyleOrigin = LifeStyle(date, "Running", "2 hr", "1510 kcal")
        val lifeStyleRevision = LifeStyle(date, "Sleeping", "22 hr", "348 kcal")

        val lifeStyleOriginRequestDto = LifeStyleRequestDto(
            lifeStyleOrigin.date,
            lifeStyleOrigin.name,
            lifeStyleOrigin.time,
            lifeStyleOrigin.burnedCalorie
        )
        val lifeStyleRevisionRequestDto = LifeStyleRequestDto(
            lifeStyleRevision.date,
            lifeStyleRevision.name,
            lifeStyleRevision.time,
            lifeStyleRevision.burnedCalorie
        )

        val expected = DataNotFoundException("Data No Exist. Create Before Delete.")
        coEvery {
            repository.update(
                lifeStyleOriginRequestDto,
                lifeStyleRevisionRequestDto
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            updateUseCase(
                lifeStyleOriginRequestDto,
                lifeStyleRevisionRequestDto
            )
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }

    @Test
    fun `네트워크 문제_활동 갱신 실패`() {
        val date = DateTime.now()
        val lifeStyleOrigin = LifeStyle(date, "Running", "2 hr", "1510 kcal")
        val lifeStyleRevision = LifeStyle(date, "Sleeping", "22 hr", "348 kcal")

        val lifeStyleOriginRequestDto = LifeStyleRequestDto(
            lifeStyleOrigin.date,
            lifeStyleOrigin.name,
            lifeStyleOrigin.time,
            lifeStyleOrigin.burnedCalorie
        )
        val lifeStyleRevisionRequestDto = LifeStyleRequestDto(
            lifeStyleRevision.date,
            lifeStyleRevision.name,
            lifeStyleRevision.time,
            lifeStyleRevision.burnedCalorie
        )

        val expected = ConnectionErrorException("No Connection")
        coEvery {
            repository.update(
                lifeStyleOriginRequestDto,
                lifeStyleRevisionRequestDto
            )
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            updateUseCase(
                lifeStyleOriginRequestDto,
                lifeStyleRevisionRequestDto
            )
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }
}
