package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.repository.MealRepository
import kotlinx.coroutines.flow.Flow

class UpdateMealUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(meal: Meal): Flow<Boolean> = repository.update(meal)
}
