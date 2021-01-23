package com.example.diet.mealInfo.domain.usecase

import com.example.diet.mealInfo.domain.repository.MealInfoRepository

class DeleteMealInfoUseCase(
    private val repository: MealInfoRepository
) {
    operator fun invoke(date: String): Boolean = repository.delete(date)
}
