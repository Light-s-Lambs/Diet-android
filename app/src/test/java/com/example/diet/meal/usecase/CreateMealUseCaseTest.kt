package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import com.example.diet.meal.repository.MealRepository
import com.example.diet.meal.usecase.exception.ConnectionFailureException
import com.example.diet.meal.usecase.exception.DataAlreadyExistException
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
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class CreateMealUseCaseTest {
    lateinit var useCase: CreateMealUseCase

    @MockK
    lateinit var repository: MealRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = CreateMealUseCase(repository)
    }

    @Test
    fun `사용자의 입력을 받아 식단 생성 성공`() {
        val date = DateTime.now()
        val mealType = MealType.Breakfast
        val menuName = MealName.Toast
        val calorie = 313.0
        val expected = Meal(date, mealType, menuName, calorie)
        every {
            repository.createMeal(date, mealType, menuName, calorie)
        } returns flowOf(expected)

        runBlocking {
            useCase(date, mealType, menuName, calorie)
                .catch { fail() }
                .collect { assertEquals(expected, it) }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `연결 실패로 인해 생성 실패`() {
        val expected = ConnectionFailureException()
        val date = DateTime.now()
        val mealType = MealType.Breakfast
        val mealName = MealName.Toast
        val calorie = 313.0
        every {
            repository.createMeal(date, mealType, mealName, calorie)
        } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(date, mealType, mealName, calorie)
                .catch { assertEquals(expected::class, it::class) }
                .collect { fail() }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `입력한 식단과 동일한 메뉴가 동일한 식사시간에 있어서 생성 실패`() {
        val expected = DataAlreadyExistException()
        val date = DateTime.now()
        val mealType = MealType.Breakfast
        val mealName = MealName.Toast
        val calorie = 313.0
        every {
            repository.createMeal(date, mealType, mealName, calorie)
        } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(date, mealType, mealName, calorie)
                .catch { assertEquals(expected::class, it::class) }
                .collect { fail() }
        }
    }
}
