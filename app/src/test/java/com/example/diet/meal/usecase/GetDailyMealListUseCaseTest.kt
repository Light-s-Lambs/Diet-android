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

class GetDailyMealListUseCaseTest {
    lateinit var useCase: GetDailyMealListUseCase

    @MockK
    lateinit var repository: MealRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetDailyMealListUseCase(repository)
    }

    @Test
    fun `주어진 날짜의 식단 정보 목록 가져오기 성공`() {
        val expected = mutableListOf<Meal>()
        val date = DateTime.now()
        val mealType1 = MealType.Breakfast
        val mealName1 = MealName.Toast
        val calorie1 = 313.0
        val mealType2 = MealType.Lunch
        val menuName2 = MealName.Noodle
        val calorie2 = 495.0
        val meal1 = Meal(date, mealType1, mealName1, calorie1)
        val meal2 = Meal(date, mealType2, menuName2, calorie2)
        expected.add(meal1)
        expected.add(meal2)
        every { repository.getDailyMealList(date) } returns flowOf(expected)

        runBlocking {
            useCase(date)
                .catch { Assert.fail() }
                .collect { Assert.assertEquals(expected::class, it::class) }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `연결 실패로 인해 목록 가져오기 실패`() {
        val expected = ConnectionFailureException()
        val targetList = mutableListOf<Meal>()
        val date = DateTime.now()
        val mealType1 = MealType.Breakfast
        val mealName1 = MealName.Toast
        val calorie1 = 313.0
        val mealType2 = MealType.Lunch
        val menuName2 = MealName.Noodle
        val calorie2 = 495.0
        val meal1 = Meal(date, mealType1, mealName1, calorie1)
        val meal2 = Meal(date, mealType2, menuName2, calorie2)
        targetList.add(meal1)
        targetList.add(meal2)
        every { repository.getDailyMealList(date) } returns callbackFlow { close(expected) }

        runBlocking {
            useCase(date)
                .catch { Assert.assertEquals(expected::class, it::class) }
                .collect { Assert.fail() }
        }
    }
}
