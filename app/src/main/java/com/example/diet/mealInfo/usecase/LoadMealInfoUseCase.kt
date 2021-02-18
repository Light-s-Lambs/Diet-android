package com.example.diet.mealInfo.usecase

import com.example.diet.mealInfo.model.MealInfo
import com.example.diet.mealInfo.repository.MealInfoRepository

class LoadMealInfoUseCase(
    private val repository: MealInfoRepository
) {
    operator fun invoke(date: String): MealInfo = repository.load(date)
}
