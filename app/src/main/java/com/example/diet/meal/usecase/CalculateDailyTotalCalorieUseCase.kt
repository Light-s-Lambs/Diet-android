package com.example.diet.meal.usecase

import com.example.diet.meal.model.Meal

class CalculateDailyTotalCalorieUseCase {
    operator fun invoke(dailyMealList: List<Meal>) =
        dailyMealList.sumByDouble { meal -> meal.calorie }
}
