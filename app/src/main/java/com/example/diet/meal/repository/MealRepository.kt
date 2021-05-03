package com.example.diet.meal.repository

import com.example.diet.meal.model.Meal
import com.example.diet.meal.usecase.MealRequestModel
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface MealRepository {
    fun createMeal(mealRequestModel: MealRequestModel): Flow<Meal>

    fun deleteMeal(mealRequestModel: MealRequestModel): Flow<Unit>

    fun getDailyMealList(date: DateTime): Flow<List<Meal>>

    fun updateMeal(
        from: MealRequestModel,
        to: MealRequestModel
    ): Flow<Meal>
}
