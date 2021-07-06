package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CalculateDailyTotalCalorieUseCase {
    operator fun invoke(mealListFlow: Flow<List<Meal>>): Flow<Double> =
        mealListFlow.map { it.sumByDouble { meal -> meal.calorie } }
}
