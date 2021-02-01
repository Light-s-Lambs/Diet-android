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
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()

        every { repository.load(dateString) } returns lifeStyleInfo

        val actual = loadUseCase(dateString)

        assertEquals(actual, lifeStyleInfo)
        verify { repository.load(dateString) }
    }

    @Test
    fun testInvoke_whenRepoHasNoMatchData_returnEmptyInfo() {
        val lifeStyleList = listOf<LifeStyle>()
        val lifeStyleInfo = LifeStyleInfo(0, 0, lifeStyleList)
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()

        every { repository.load(dateString) } returns lifeStyleInfo

        val actual = loadUseCase(dateString)

        assertEquals(actual, lifeStyleInfo)
        verify { repository.load(dateString) }
    }
}
