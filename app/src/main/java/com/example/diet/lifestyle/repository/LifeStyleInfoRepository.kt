package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.LifeStyleInfo

interface LifeStyleInfoRepository {
    fun save(date: String, lifeStyleInfo: LifeStyleInfo): Boolean
    fun load(date: String): LifeStyleInfo
    fun delete(date: String): Boolean
}
