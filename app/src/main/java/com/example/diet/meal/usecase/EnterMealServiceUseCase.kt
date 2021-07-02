package com.example.diet.meal.usecase

import com.example.diet.meal.repository.MealRepository
import com.example.diet.meal.service.MealService
import kotlinx.coroutines.flow.map
import org.joda.time.DateTime

class EnterMealServiceUseCase(
    private val mealService: MealService,
    private val repository: MealRepository,
    private val calculateDailyTotalCalorieUseCase: CalculateDailyTotalCalorieUseCase
) {
    operator fun invoke(date: DateTime) {
        calculateDailyTotalCalorieUseCase(repository.getDailyMealList(date))
            .map { totalCalorie -> mealService.renderingDailyTotalCalorie(date, totalCalorie) }
        repository.getDailyMealList(date)
            .map { dailyMealList -> mealService.renderingDailyMealList(date, dailyMealList) }
    }
}