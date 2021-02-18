package com.example.diet.mealInfo.usecase

import com.example.diet.mealInfo.repository.MealInfoRepository

class DeleteMealInfoUseCase(
    private val repository: MealInfoRepository
) {
    operator fun invoke(date: String): Boolean = repository.delete(date)
}
