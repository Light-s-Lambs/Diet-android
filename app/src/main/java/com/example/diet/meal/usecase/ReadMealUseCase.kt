package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

class ReadMealUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(date: DateTime): Flow<Meal> = repository.read(date)
}
