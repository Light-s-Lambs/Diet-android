package com.example.diet.meal.repository

import com.example.diet.meal.model.Meal
import org.joda.time.DateTime

interface MealRepository {
    fun save(meal: Meal): Boolean
    fun load(date: DateTime): Meal
    fun update(meal: Meal): Boolean
    fun delete(date: DateTime): Boolean
}
