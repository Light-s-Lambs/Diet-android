package com.example.diet.meal.repository

import com.example.diet.meal.model.Meal
import org.joda.time.DateTime

interface MealRepository {
    fun create(meal: Meal): Boolean
    fun read(date: DateTime): Meal
    fun update(meal: Meal): Boolean
    fun delete(date: DateTime): Boolean
}
