package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealType
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class CalculateDailyTotalCalorieUseCaseTest {
    lateinit var useCase: CalculateDailyTotalCalorieUseCase

    @Before
    fun setUp() {
        useCase = CalculateDailyTotalCalorieUseCase()
    }

    @Test
    fun `비어있는 식단 목록 Flow가 주어졌을 때_식단의 총 칼로리 반환 성공`() {
        val dailyMealList = emptyList<Meal>()
        val dailyMealListFlow = flowOf(dailyMealList)
        val expected = 0.0

        runBlocking {
            useCase(dailyMealListFlow)
                .catch { fail() }
                .collect { assertEquals(expected, it, 0.1) }
        }
    }

    @Test
    fun `비어있지 않은 식단 목록 Flow가 주어졌을 때_식단의 총 칼로리 반환 성공`() {
        val dailyMealList = mutableListOf<Meal>()
        val date = DateTime.now()
        val mealType1 = MealType.Breakfast
        val mealName1 = "Toast"
        val calorie1 = 313.0
        val mealType2 = MealType.Lunch
        val menuName2 = "Noodle"
        val calorie2 = 495.0
        val meal1 = Meal(date, mealType1, mealName1, calorie1)
        val meal2 = Meal(date, mealType2, menuName2, calorie2)
        dailyMealList.add(meal1)
        dailyMealList.add(meal2)
        val dailyMealListFlow = flowOf(dailyMealList)

        val expected = dailyMealList.sumByDouble { meal -> meal.calorie }

        runBlocking {
            useCase(dailyMealListFlow)
                .catch { fail() }
                .collect { assertEquals(expected, it, 0.1) }
        }
    }
}
