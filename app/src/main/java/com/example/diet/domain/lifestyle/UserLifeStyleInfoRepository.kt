package com.example.diet.domain.lifestyle

interface UserLifeStyleInfoRepository {
    fun save(userLifeStyleInfo: UserLifeStyleInfo): Boolean
    fun load(date: String): UserLifeStyleInfo
    fun update(userLifeStyleInfo: UserLifeStyleInfo): Boolean
    fun delete(date: String): Boolean
}
