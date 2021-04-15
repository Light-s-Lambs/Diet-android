package com.example.diet.meal.model

import org.joda.time.DateTime

enum class MealType {
    Breakfast,
    Lunch,
    Dinner,
    Dessert,
    Snack
}

enum class MealName {
    Toast,
    Noodle,
    Steak,
    Apple,
    Chocolate
}

data class Meal(
    val date: DateTime,
    val mealType: MealType,
    val menuName: MealName,
    val calorie: Double
)
