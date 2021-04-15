package com.example.diet.meal.repository

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface MealRepository {
    fun createMeal(
        date: DateTime,
        mealType: MealType,
        mealName: MealName,
        calorie: Double
    ): Flow<Meal>

    fun deleteMeal(meal: Meal): Flow<Unit>

    fun getDailyMealList(date: DateTime): Flow<List<Meal>>

    fun updateMeal(
        from: Meal,
        date: DateTime,
        mealType: MealType,
        mealName: MealName,
        calorie: Double
    ): Flow<Meal>
}
