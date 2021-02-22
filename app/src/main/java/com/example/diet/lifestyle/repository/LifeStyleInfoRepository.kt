package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.LifeStyleInfo
import kotlinx.coroutines.flow.Flow

interface LifeStyleInfoRepository {
    fun save(date: String, lifeStyleInfo: LifeStyleInfo): Flow<Boolean>
    fun load(date: String): Flow<LifeStyleInfo>
    fun delete(date: String): Flow<Boolean>
}
