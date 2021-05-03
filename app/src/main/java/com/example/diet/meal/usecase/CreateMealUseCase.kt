package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.repository.MealRepository
import kotlinx.coroutines.flow.Flow

class CreateMealUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(
        mealRequestModel: MealRequestModel
    ): Flow<Meal> = repository.createMeal(mealRequestModel)
}
