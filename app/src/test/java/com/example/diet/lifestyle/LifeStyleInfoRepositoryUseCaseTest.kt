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
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class LifeStyleInfoRepositoryUseCaseTest {
    val repository: LifeStyleInfoRepository = mockk()

    @Before
    fun defineMockkBehavior() {
    }

    @Test
    fun deleteLifeStyleInfoUseCaseTest() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()

        every {
            repository.delete(dateString)
        } returns true

        val deleteLifeStyleInfoUseCase = DeleteLifeStyleInfoUseCase(repository)

        assertEquals(deleteLifeStyleInfoUseCase(dateString), true)
        verify {
            repository.delete(dateString)
        }
    }

    @Test
    fun loadLifeStyleInfo() {
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()

        every {
            repository.load(dateString)
        } returns lifeStyleInfo

        val loadLifeStyleInfoUseCase = LoadLifeStyleInfoUseCase(repository)
        assertEquals(lifeStyleInfo, loadLifeStyleInfoUseCase(dateString))
        verify {
            repository.load(dateString)
        }
    }

    @Test
    fun saveTest() {
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
        every {
            repository.save(dateString, lifeStyleInfo)
        } returns true

        val saveLifeStyleInfoUseCase = SaveLifeStyleInfoUseCase(repository)

        assertEquals(true, saveLifeStyleInfoUseCase(dateString, lifeStyleInfo))
        verify {
            repository.save(dateString, lifeStyleInfo)
        }
    }

    @Test
    fun updateLifeStyleInfo() {
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()

        every {
            repository.update(dateString, lifeStyleInfo)
        } returns true

        val updateLifeStyleInfoUseCase = UpdateLifeStyleInfoUseCase(repository)

        assertEquals(true, updateLifeStyleInfoUseCase(dateString, lifeStyleInfo))
        verify {
            repository.update(dateString, lifeStyleInfo)
        }
    }
}
