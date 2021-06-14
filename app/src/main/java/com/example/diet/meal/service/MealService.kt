package com.example.diet.meal.service

import com.example.diet.meal.model.Meal
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface MealService {
    fun renderingDailyTotalCalorie(date: DateTime, totalCalorie: Double): Flow<Unit>
    fun renderingDailyMealList(date: DateTime, dailyMealList: List<Meal>): Flow<Unit>
}
