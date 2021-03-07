package com.example.diet.meal.repository

import com.example.diet.meal.model.Meal
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface MealRepository {
    fun create(meal: Meal): Flow<Boolean>
    fun read(date: DateTime): Flow<Meal>
    fun update(meal: Meal): Flow<Boolean>
    fun delete(date: DateTime): Flow<Boolean>
}
