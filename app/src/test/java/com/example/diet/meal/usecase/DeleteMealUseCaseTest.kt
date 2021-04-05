package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import com.example.diet.meal.repository.MealRepository
import com.example.diet.meal.usecase.exception.ConnectionErrorException
import com.example.diet.meal.usecase.exception.DataNoExistException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class DeleteMealUseCaseTest {
    lateinit var useCase: DeleteMealUseCase

    @MockK
    lateinit var repository: MealRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DeleteMealUseCase(repository)
    }

    @Test
    fun `객체가 있고 삭제 성공`() {
        val date = DateTime.now()
        val targetObject = Meal(
            date,
            MealType.Breakfast,
            MealName.Toast,
            "313"
        )
        every { repository.delete(targetObject) } returns flowOf(Unit)

        runBlocking {
            useCase(targetObject)
                .catch { Assert.fail() }
                .collect { Assert.assertEquals(Unit, it) }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `연결 실패로 인해 삭제 실패`() {
        val expected = ConnectionErrorException()
        val date = DateTime.now()
        val targetObject = Meal(
            date,
            MealType.Breakfast,
            MealName.Toast,
            "313"
        )
        every { repository.delete(targetObject) } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(targetObject)
                .catch { Assert.assertEquals(expected::class, it::class) }
                .collect { Assert.fail() }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `객체가 없어서 삭제 실패`() {
        val expected = DataNoExistException()
        val date = DateTime.now()
        val targetObject = Meal(
            date,
            MealType.Breakfast,
            MealName.Toast,
            "313"
        )
        every { repository.delete(targetObject) } returns callbackFlow { close() }

        runBlocking {
            useCase(targetObject)
                .catch { Assert.assertEquals(expected::class, it::class) }
                .collect { Assert.fail() }
        }
    }
}
