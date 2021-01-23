package com.example.diet.mealInfo.domain.usecase

import com.example.diet.mealInfo.domain.model.MealInfo
import com.example.diet.mealInfo.domain.repository.MealInfoRepository

class UpdateMealInfoUseCase(
    private val repository: MealInfoRepository
) {
    operator fun invoke(mealInfo: MealInfo): Boolean = repository.update(mealInfo)
}
