package com.example.diet.meal.model

data class Meal(
    val date: String,
    val mealType/*BreakFast, Lunch, Dinner, Dessert, Snack etc.*/: String,
    val menu/*Apple, Rice, Beef, Kimchi etc.*/: String,
    val calorie/*Calorie of Menu*/: String
)
