package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import com.example.diet.meal.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

class CreateMealUseCase(
    private val repository: MealRepository
) {
    operator fun invoke(
        date: DateTime,
        mealType: MealType,
        mealName: MealName,
        calorie: Double
    ): Flow<Meal> = repository.createMeal(date, mealType, mealName, calorie)
}
