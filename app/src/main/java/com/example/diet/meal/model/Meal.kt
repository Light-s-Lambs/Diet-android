package com.example.diet.meal.model

import org.joda.time.DateTime

enum class MealType {
    Breakfast,
    Lunch,
    Dinner,
    Dessert,
    Snack,
    etc
}

enum class MealName {
    Toast,
    Noodle,
    Steak,
    Apple,
    Snack,
    Chocolate,
    etc
}

data class Meal(
    val date: DateTime,
    val mealType: MealType,
    val menuName: MealName,
    val calorie: String
)
