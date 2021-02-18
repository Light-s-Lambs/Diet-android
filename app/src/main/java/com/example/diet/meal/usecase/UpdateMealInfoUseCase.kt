package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.repository.MealRepository

class UpdateMealInfoUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(meal: Meal): Boolean = repository.update(meal)
}
