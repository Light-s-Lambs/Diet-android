package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
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
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()

        val expected = true

        coEvery { repository.delete(dateString) } returns flowOf(expected)

        runBlocking {
            deleteUseCase(dateString, onSuccess = {
                assertEquals(it, expected)
            }, onFailed = {
                assertEquals(it, expected)
            }, onError = {
                assertEquals(it, expected)
            })
        }

        coVerify { repository.delete(dateString) }
    }

    @Test
    fun testInvoke_whenRepoHasNoMatchData_returnFalse() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()

        val expected = false

        coEvery { repository.delete(dateString) } returns flowOf(expected)

        runBlocking {
            deleteUseCase(dateString, onSuccess = {
                assertEquals(it, expected)
            }, onFailed = {
                assertEquals(it, expected)
            }, onError = {
                assertEquals(it, expected)
            })
        }

        coVerify { repository.delete(dateString) }
    }


    @Test
    fun testInvoke_whenRepoOccursException_raiseException() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
        val exception = Exception()

        val expected = exception

        coEvery { repository.delete(dateString) } throws exception

        runBlocking {
            deleteUseCase(dateString, onSuccess = {
                assertEquals(it, expected)
            }, onFailed = {
                assertEquals(it, expected)
            }, onError = {
                assertEquals(it, expected)
            })
        }

        coVerify { repository.delete(dateString) }
    }
}
