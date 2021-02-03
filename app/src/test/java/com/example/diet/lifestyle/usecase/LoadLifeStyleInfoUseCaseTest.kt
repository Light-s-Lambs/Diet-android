package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
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

class LoadLifeStyleInfoUseCaseTest {
    lateinit var loadUseCase: LoadLifeStyleInfoUseCase

    @MockK
    lateinit var repository: LifeStyleInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadUseCase = LoadLifeStyleInfoUseCase(repository)
    }

    @Test
    fun testInvoke_whenRepoHasMatchData_returnMatchInfo() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val expected = LifeStyleInfo(1900, 3758, lifeStyleList)

        every { repository.load(dateString) } returns expected

        val actual = loadUseCase(dateString)

        assertEquals(actual, expected)
        verify { repository.load(dateString) }
    }

    @Test
    fun testInvoke_whenRepoHasNoMatchData_returnsEmptyInfo() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
        val exception = Exception("Empty")
        val expected = true

        every { repository.load(dateString) } throws exception

        val actual = loadUseCase(dateString)

        assertEquals(expected, actual.lifeStyleList.isEmpty())
        verify { repository.load(dateString) }
    }
}
