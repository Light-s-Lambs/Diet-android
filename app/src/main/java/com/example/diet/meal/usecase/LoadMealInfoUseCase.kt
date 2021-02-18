package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.repository.MealRepository

class LoadMealInfoUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(date: String): Meal = repository.load(date)
}
