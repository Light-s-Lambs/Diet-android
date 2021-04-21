package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import com.example.diet.meal.repository.MealRepository
import com.example.diet.meal.usecase.exception.ConnectionFailureException
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
    fun `선택한 객체 삭제 성공`() {
        val date = DateTime.now()
        val mealType = MealType.Breakfast
        val mealName = MealName.Toast
        val calorie = 313.0
        val targetObject = Meal(date, mealType, mealName, calorie)
        every { repository.deleteMeal(targetObject) } returns flowOf(Unit)

        runBlocking {
            useCase(targetObject)
                .catch { Assert.fail() }
                .collect { Assert.assertEquals(Unit, it) }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `연결 실패로 인해 삭제 실패`() {
        val expected = ConnectionFailureException()
        val date = DateTime.now()
        val mealType = MealType.Breakfast
        val mealName = MealName.Toast
        val calorie = 313.0
        val targetObject = Meal(date, mealType, mealName, calorie)
        every { repository.deleteMeal(targetObject) } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(targetObject)
                .catch { Assert.assertEquals(expected::class, it::class) }
                .collect { Assert.fail() }
        }
    }
}
