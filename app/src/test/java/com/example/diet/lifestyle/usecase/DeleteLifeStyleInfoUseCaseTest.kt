package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*


class DeleteLifeStyleInfoUseCaseTest {
    lateinit var deleteUseCase: DeleteLifeStyleInfoUseCase

    @MockK
    lateinit var repository: LifeStyleInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deleteUseCase = DeleteLifeStyleInfoUseCase(repository)
    }

    @Test
    fun testInvoke_whenDeleteDataSuccess() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = true

        coEvery { repository.delete(dateString) } returns flowOf(Unit)

        runBlocking {
            kotlin.runCatching {
                deleteUseCase(dateString).collect {
                    assertEquals(expected, it)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.delete(dateString) }
    }

    @Test
    fun testInvoke_whenRepoHasNoMatchData_raiseNoMatchDataException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val exception = deleteUseCase.occurNoMatchDataException()
        val expected = false

        coEvery { repository.delete(dateString) } throws exception

        runBlocking {
            kotlin.runCatching {
                deleteUseCase(dateString).collect {
                    assertEquals(expected, it)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.delete(dateString) }
    }

    @Test
    fun testInvoke_whenUnexpectedErrorOccurs_raiseUnexpectedBehaviorException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val exception = deleteUseCase.occurUnexpectedBehaviorException()
        val expected = false

        coEvery { repository.delete(dateString) } throws exception

        runBlocking {
            kotlin.runCatching {
                deleteUseCase(dateString).collect {
                    assertEquals(expected, it)
                }
            }.onFailure {
                Assert.fail()
            }
        }

        coVerify { repository.delete(dateString) }
    }
}
