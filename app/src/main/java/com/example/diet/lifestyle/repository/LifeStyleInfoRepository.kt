package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.LifeStyleInfo

interface LifeStyleInfoRepository {
    fun save(lifeStyleInfo: LifeStyleInfo): Boolean
    fun load(date: String): LifeStyleInfo
    fun update(lifeStyleInfo: LifeStyleInfo): Boolean
    fun delete(date: String): Boolean
}
