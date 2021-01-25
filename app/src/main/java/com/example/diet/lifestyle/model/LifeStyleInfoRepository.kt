package com.example.diet.lifestyle.model

interface LifeStyleInfoRepository {
    fun save(lifeStyleInfo: LifeStyleInfo): Boolean
    fun load(date: String): LifeStyleInfo
    fun update(lifeStyleInfo: LifeStyleInfo): Boolean
    fun delete(date: String): Boolean
}
