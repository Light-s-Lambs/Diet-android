package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.repository.MealRepository
import kotlinx.coroutines.flow.Flow

class DeleteMealUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(meal: Meal): Flow<Unit> = repository.delete(meal)
}
