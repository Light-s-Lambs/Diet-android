package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.LifeStyleInfo
import kotlinx.coroutines.flow.Flow

interface LifeStyleInfoRepository {
    fun load(date: String): Flow<LifeStyleInfo>
    fun delete(date: String): Flow<Unit>
    fun update(date: String, lifeStyleInfo: LifeStyleInfo): Flow<Unit>
    fun create(date: String, lifeStyleInfo: LifeStyleInfo) : Flow<Unit>
}
