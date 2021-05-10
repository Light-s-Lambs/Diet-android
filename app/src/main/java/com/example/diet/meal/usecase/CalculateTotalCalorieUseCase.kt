package com.example.diet.meal.usecase

import com.example.diet.meal.repository.MealRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.joda.time.DateTime

class CalculateTotalCalorieUseCase(
    private val repository: MealRepository
) {
    suspend operator fun invoke(date: DateTime) = flowOf(repository.getDailyMealList(date).first().sumByDouble { meal -> meal.calorie })
}
