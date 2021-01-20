package com.example.diet.lifestyle.domain

interface UserLifeStyleInfoRepository {
    fun save(userLifeStyleInfo: UserLifeStyleInfo): Boolean
    fun load(date: String): UserLifeStyleInfo
    fun update(userLifeStyleInfo: UserLifeStyleInfo): Boolean
    fun delete(date: String): Boolean
}
