package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import kotlinx.coroutines.flow.Flow

interface LifeStyleInfoRepository {
    fun load(date: String): Flow<LifeStyleInfo>
    fun delete(date: String): Flow<LifeStyleInfo>
    fun update(date: String, basalMetabolism : Int, activityMetabolism : Int, lifeStyleList: List<LifeStyle>): Flow<LifeStyleInfo>
    fun create(date: String, basalMetabolism : Int, activityMetabolism : Int, lifeStyleList: List<LifeStyle>) : Flow<LifeStyleInfo>
}
