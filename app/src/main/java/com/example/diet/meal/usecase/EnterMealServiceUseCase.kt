package com.example.diet.meal.usecase

import com.example.diet.meal.repository.MealRepository
import com.example.diet.meal.service.MealService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.joda.time.DateTime

class EnterMealServiceUseCase(
    private val mealService: MealService,
    private val repository: MealRepository
) {
    suspend operator fun invoke(date: DateTime): Flow<Unit> {
        val dailyMealList = repository.getDailyMealList(date).first()
        val calculateTotalCalorieUseCase = CalculateDailyTotalCalorieUseCase(repository)
        val totalCalorie = calculateTotalCalorieUseCase(date).first()
        mealService.refreshDailyMealListUI(date, totalCalorie, dailyMealList)
        return flowOf()
    }
}
