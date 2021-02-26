package com.example.diet.meal.usecase

import com.example.diet.meal.repository.MealRepository
import org.joda.time.DateTime

class DeleteMealUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(date: DateTime): Boolean = repository.delete(date)
}
