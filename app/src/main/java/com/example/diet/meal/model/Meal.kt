package com.example.diet.meal.model

enum class MealType{
    Breakfast,
    Lunch,
    Dinner,
    Dessert,
    Snack,
    etc
}

enum class MealName{
    Toast,
    Noodle,
    Steak,
    Apple,
    Snack,
    Chocolate,
    etc
}

data class Meal(
    val date: String,
    val mealType : MealType,
    val menuName: MealName,
    val calorie: String
)
