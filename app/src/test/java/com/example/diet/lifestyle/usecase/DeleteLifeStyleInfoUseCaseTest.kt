package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.DataNoExistException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
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
    fun `객체 있음_삭제 성공`() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val basalMetabolism = 1900
        val activityMetabolism = 3758
        val lifeStyleList = listOf(
            LifeStyle("Sleeping", "22 hr", "348 kcal"),
            LifeStyle("Running", "2 hr", "1510 kcal")
        )
        val lifeStyleInfo = LifeStyleInfo(basalMetabolism, activityMetabolism, lifeStyleList)
        val expected = lifeStyleInfo
        coEvery {
            repository.delete(dateString)
        } returns flowOf(expected)

        runBlocking {
            deleteUseCase(dateString)
                .catch { fail() }
                .collect {
                    assertEquals(expected, it)
                }
        }
    }

    @Test
    fun `객체 없음_삭제 실패`() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val expected = DataNoExistException("Data No Exist. Create Before Delete.")
        coEvery {
            repository.delete(dateString)
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            deleteUseCase(dateString)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }

    @Test
    fun `연결 문제_삭제 실패`() {
        val dateString = DateTime.now()
            .toString(DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.getDefault()))
        val expected = UnexpectedBehaviorException("No Connection")
        coEvery {
            repository.delete(dateString)
        } returns callbackFlow {
            close(expected)
        }

        runBlocking {
            deleteUseCase(dateString)
                .catch {
                    assertEquals(expected::class, it::class)
                }
                .collect {
                    fail()
                }
        }
    }
}
