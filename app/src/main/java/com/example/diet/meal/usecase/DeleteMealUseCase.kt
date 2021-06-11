package com.example.diet.meal.usecase

import com.example.diet.meal.repository.MealRepository
import kotlinx.coroutines.flow.Flow

class DeleteMealUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(
        mealRequestModel: MealRequestModel
    ): Flow<Unit> = repository.deleteMeal(mealRequestModel)
}
