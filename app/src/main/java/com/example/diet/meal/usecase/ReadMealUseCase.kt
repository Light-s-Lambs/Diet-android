package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.repository.MealRepository
import org.joda.time.DateTime

class ReadMealUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(date: DateTime): Meal = repository.read(date)
}
