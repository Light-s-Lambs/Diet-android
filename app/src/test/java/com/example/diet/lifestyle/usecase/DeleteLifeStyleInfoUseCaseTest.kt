package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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

        every { repository.delete(dateString) } returns true

        val actual = deleteUseCase(dateString)

        assertTrue(actual)
        verify { repository.delete(dateString) }
    }

    @Test
    fun testInvoke_whenRepoHasNoMatchData_returnFalse() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()

        every { repository.delete(dateString) } returns false

        val actual = deleteUseCase(dateString)

        assertFalse(actual)
        verify { repository.delete(dateString) }
    }
}
