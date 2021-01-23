package com.example.diet.mealInfo.domain.usecase

import com.example.diet.mealInfo.domain.model.MealInfo
import com.example.diet.mealInfo.domain.repository.MealInfoRepository

class LoadMealInfoUseCase(
    private val repository: MealInfoRepository
) {
    operator fun invoke(date: String): MealInfo = repository.load(date)
}
