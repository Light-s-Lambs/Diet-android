package com.example.diet.mealInfo.usecase

import com.example.diet.mealInfo.model.MealInfo
import com.example.diet.mealInfo.repository.MealInfoRepository

class SaveMealInfoUseCase(
    private val repository: MealInfoRepository
) {
    operator fun invoke(mealInfo: MealInfo): Boolean = repository.save(mealInfo)
}
