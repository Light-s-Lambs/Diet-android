package com.example.diet.meal.repository

import com.example.diet.meal.model.Meal
import com.example.diet.meal.model.MealName
import com.example.diet.meal.model.MealType
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface MealRepository {
    fun create(
        date: DateTime,
        mealType: MealType,
        mealName: MealName,
        calorie: String
    ): Flow<Meal>

    fun getList(date: DateTime): Flow<List<Meal>>
    fun update(from: Meal, to: Meal): Flow<Meal>
    fun delete(meal: Meal): Flow<Unit>
}
