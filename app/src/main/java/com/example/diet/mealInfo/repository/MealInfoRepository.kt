package com.example.diet.mealInfo.repository

import com.example.diet.mealInfo.model.MealInfo

interface MealInfoRepository {
    fun save(mealInfo: MealInfo): Boolean
    fun load(date: String): MealInfo
    fun update(mealInfo: MealInfo): Boolean
    fun delete(date: String): Boolean
}
