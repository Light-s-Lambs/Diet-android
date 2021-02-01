package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class SaveLifeStyleInfoUseCaseTest {
    lateinit var saveUseCase: SaveLifeStyleInfoUseCase

    @MockK
    lateinit var repository: LifeStyleInfoRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        saveUseCase = SaveLifeStyleInfoUseCase(repository)
    }

    @Test
    fun testInvoke_whenSuccessSaveData_returnTrue() {
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()

        every { repository.save(dateString, lifeStyleInfo) } returns true

        val actual = saveUseCase(dateString, lifeStyleInfo)

        assertTrue(actual)
        verify { repository.save(dateString, lifeStyleInfo) }
    }
}
