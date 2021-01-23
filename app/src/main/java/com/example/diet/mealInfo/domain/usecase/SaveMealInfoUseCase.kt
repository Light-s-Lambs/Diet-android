package com.example.diet.mealInfo.domain.usecase

import com.example.diet.mealInfo.domain.model.MealInfo
import com.example.diet.mealInfo.domain.repository.MealInfoRepository

class SaveMealInfoUseCase(
    private val repository: MealInfoRepository
) {
    operator fun invoke(mealInfo: MealInfo): Boolean = repository.save(mealInfo)
}
