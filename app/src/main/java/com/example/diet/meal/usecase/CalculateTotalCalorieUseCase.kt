package com.example.diet.meal.usecase

import com.example.diet.extension.flattenToList
import com.example.diet.meal.repository.MealRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.joda.time.DateTime

class CalculateTotalCalorieUseCase(
    private val repository: MealRepository
) {
    @FlowPreview
    suspend operator fun invoke(date: DateTime) : Flow<Double> {
        return flowOf(repository.getDailyMealList(date).flattenToList().sumByDouble{ meal -> meal.calorie })
    }
}
