package com.example.diet.meal.model

import org.joda.time.DateTime

data class Meal(
    val date: DateTime,
    val mealType: MealType,
    val mealName: String,
    val calorie: Double
)
