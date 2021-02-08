package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

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

        every { repository.delete(dateString) } returns expected

        var actual by Delegates.notNull<Boolean>()
        deleteUseCase(dateString, onSuccess = {
            actual = it
        }, onError = {
            actual = false
        })

        assertEquals(actual, expected)
        verify { repository.delete(dateString) }
    }

    @Test
    fun testInvoke_whenRepoHasNoMatchData_returnFalse() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()

        val expected = false

        every { repository.delete(dateString) } returns expected

        var actual by Delegates.notNull<Boolean>()
        deleteUseCase(dateString, onSuccess = {
            actual = it
        }, onError = {
            actual = false
        })

        assertEquals(actual, expected)
        verify { repository.delete(dateString) }
    }

    @Test
    fun testInvoke_whenRepoOccursException_returnFalse() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
        val exception = Exception()

        val expected = false

        every { repository.delete(dateString) } throws exception

        var actual by Delegates.notNull<Boolean>()
        deleteUseCase(dateString, onSuccess = {
            actual = it
        }, onError = {
            actual = false
        })

        assertEquals(actual, expected)
        verify { repository.delete(dateString) }
    }
}
