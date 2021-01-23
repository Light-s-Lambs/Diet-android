package com.example.diet.mealInfo.domain.model

data class MealInfo(
    val date: String,
    val totalCalorie: Int,
    val mealList: List<Meal>
)
