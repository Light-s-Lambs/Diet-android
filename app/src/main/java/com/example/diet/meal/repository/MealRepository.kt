package com.example.diet.meal.repository

import com.example.diet.meal.model.Meal

interface MealRepository {
    fun save(meal: Meal): Boolean
    fun load(date: String): Meal
    fun update(meal: Meal): Boolean
    fun delete(date: String): Boolean
}
