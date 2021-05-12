package com.example.diet.meal.service

import com.example.diet.meal.model.Meal
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface MealService {
    fun refreshDailyMealListUI(date: DateTime, totalCalorie: Double, dailyMealList: List<Meal>): Flow<Unit>
}
