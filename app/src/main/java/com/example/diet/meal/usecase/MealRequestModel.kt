package com.example.diet.meal.usecase

import com.example.diet.meal.model.MealType
import org.joda.time.DateTime

data class MealRequestModel(
    val date: DateTime,
    val mealType: MealType,
    val mealName: String,
    val calorie: Double
)
