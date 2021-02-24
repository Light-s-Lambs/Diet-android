package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
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
    fun testInvoke_whenRepoHasMatchData_returnTrue() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = true

        coEvery { repository.delete(dateString) } returns expected

        runBlocking {
            val deleteFlow: Flow<Boolean> = deleteUseCase(dateString)
            try {
                deleteFlow.collect {
                    assertEquals(it, expected)
                }
            } catch (e: Throwable) {
                assertEquals(e, expected)
            }
        }

        coVerify { repository.delete(dateString) }
    }

    @Test
    fun testInvoke_whenRepoHasNoMatchData_returnFalse() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val expected = false

        coEvery { repository.delete(dateString) } returns expected

        runBlocking {
            val deleteFlow: Flow<Boolean> = deleteUseCase(dateString)
            try {
                deleteFlow.collect {
                    assertEquals(it, expected)
                }
            } catch (e: Throwable) {
                assertEquals(e, expected)
            }
        }

        coVerify { repository.delete(dateString) }
    }


    @Test
    fun testInvoke_whenRepoOccursException_raiseException() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))

        val exception = Exception()

        val expected = exception

        coEvery { repository.delete(dateString) } throws exception

        runBlocking {
            val deleteFlow: Flow<Boolean> = deleteUseCase(dateString)
            try {
                deleteFlow.collect {
                    assertEquals(it, expected)
                }
            } catch (e: Throwable) {
                assertEquals(e, expected)
            }
        }

        coVerify { repository.delete(dateString) }
    }
}
