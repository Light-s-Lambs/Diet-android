package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.repository.MealRepository

class CreateMealUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(meal: Meal): Boolean = repository.create(meal)
}
