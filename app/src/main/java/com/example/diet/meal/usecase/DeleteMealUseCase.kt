package com.example.diet.meal.usecase

import com.example.diet.meal.repository.MealRepository

class DeleteMealUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(date: String): Boolean = repository.delete(date)
}
