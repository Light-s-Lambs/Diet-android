package com.example.diet.lifestyle

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.DeleteLifeStyleInfoUseCase
import com.example.diet.lifestyle.usecase.LoadLifeStyleInfoUseCase
import com.example.diet.lifestyle.usecase.SaveLifeStyleInfoUseCase
import com.example.diet.lifestyle.usecase.UpdateLifeStyleInfoUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class LifeStyleInfoRepositoryTest {
    private val lifeStyleList = listOf<LifeStyle>(
        LifeStyle("Sleeping", "22 hr", "348 kcal"),
        LifeStyle("Running", "2 hr", "1510 kcal")
    )
    private val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)
    private val repository: LifeStyleInfoRepository = mockk()

    @Test
    fun deleteLifeStyleInfo() {
        val deleteLifeStyleInfoUseCase = DeleteLifeStyleInfoUseCase(repository)
        every {
            deleteLifeStyleInfoUseCase("2020-01-26")
        } returns true
        val result = deleteLifeStyleInfoUseCase("2020-01-26")
        assertEquals(true, result)
    }

    @Test
    fun loadLifeStyleInfo() {
        val loadLifeStyleInfoUseCase = LoadLifeStyleInfoUseCase(repository)
        every {
            loadLifeStyleInfoUseCase("2020-01-26")
        } returns lifeStyleInfo
        val result = loadLifeStyleInfoUseCase("2020-01-26")
        assertEquals(lifeStyleInfo, result)
    }

    @Test
    fun saveTest() {
        val saveLifeStyleInfoUseCase = SaveLifeStyleInfoUseCase(repository)
        every {
            saveLifeStyleInfoUseCase("2020-01-26", lifeStyleInfo)
        } returns true
        val result = saveLifeStyleInfoUseCase("2020-01-26", lifeStyleInfo)
        assertEquals(true, result)
    }

    @Test
    fun updateLifeStyleInfo() {
        val updateLifeStyleInfoUseCase = UpdateLifeStyleInfoUseCase(repository)
        every {
            updateLifeStyleInfoUseCase("2020-01-26", lifeStyleInfo)
        } returns true
        val result = updateLifeStyleInfoUseCase("2020-01-26", lifeStyleInfo)
        assertEquals(true, result)
    }
}
