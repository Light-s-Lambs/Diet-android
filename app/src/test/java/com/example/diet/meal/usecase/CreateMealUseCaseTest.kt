package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
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
        val mealName = "Toast"
        val calorie = 313.0
        val mealRequestModel = MealRequestModel(date, mealType, mealName, calorie)
        val expected = Meal(date, mealType, mealName, calorie)
        every {
            repository.createMeal(mealRequestModel)
        } returns flowOf(expected)

        runBlocking {
            useCase(mealRequestModel)
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
        val mealName = "Toast"
        val calorie = 313.0
        val mealRequestModel = MealRequestModel(date, mealType, mealName, calorie)
        every {
            repository.createMeal(mealRequestModel)
        } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(mealRequestModel)
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
        val mealName = "Toast"
        val calorie = 313.0
        val mealRequestModel = MealRequestModel(date, mealType, mealName, calorie)
        every {
            repository.createMeal(mealRequestModel)
        } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(mealRequestModel)
                .catch { assertEquals(expected::class, it::class) }
                .collect { fail() }
        }
    }
}
