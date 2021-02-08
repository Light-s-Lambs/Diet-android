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
import kotlin.properties.Delegates

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
    fun testInvoke_whenSaveDataSuccess_returnTrue() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = true

        every { repository.save(dateString, lifeStyleInfo) } returns expected

        var actual by Delegates.notNull<Boolean>()
        saveUseCase(dateString, lifeStyleInfo, onSuccess = {
            actual = it
        }, onError = {
            actual = false
        })

        assertEquals(actual, expected)
        verify { repository.save(dateString, lifeStyleInfo) }
    }

    @Test
    fun testInvoke_whenSaveDataFail_returnFalse() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)

        val expected = false

        every { repository.save(dateString, lifeStyleInfo) } returns expected

        var actual by Delegates.notNull<Boolean>()
        saveUseCase(dateString, lifeStyleInfo, onSuccess = {
            actual = it
        }, onError = {
            actual = false
        })

        assertEquals(actual, expected)
        verify { repository.save(dateString, lifeStyleInfo) }
    }

    @Test
    fun testInvoke_whenOccursError_returnFalse() {
        val dateString =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).toString()
        val lifeStyleList = listOf<LifeStyle>(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(1900, 3758, lifeStyleList)
        val exception = Exception()

        val expected = false

        every { repository.save(dateString, lifeStyleInfo) } throws exception

        var actual by Delegates.notNull<Boolean>()
        saveUseCase(dateString, lifeStyleInfo, onSuccess = {
            actual = it
        }, onError = {
            actual = false
        })

        assertEquals(actual, expected)
        verify { repository.save(dateString, lifeStyleInfo) }
    }
}
