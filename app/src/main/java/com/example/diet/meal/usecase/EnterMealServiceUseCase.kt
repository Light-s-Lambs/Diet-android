package com.example.diet.meal.usecase

import com.example.diet.meal.repository.MealRepository
import com.example.diet.meal.service.MealService
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime

class EnterMealServiceUseCase(
    private val mealService: MealService,
    private val repository: MealRepository
) {
    operator fun invoke(date: DateTime) {
        val calculateDailyTotalCalorieUseCase = CalculateDailyTotalCalorieUseCase()
        repository.getDailyMealList(date).map { mealService.refreshDailyMealListUI(date, calculateDailyTotalCalorieUseCase(it), it) }
    }
}
