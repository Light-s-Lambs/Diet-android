package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface LifeStyleInfoRepository {
    fun load(date: DateTime): Flow<LifeStyleInfo>
    fun delete(date: DateTime): Flow<LifeStyleInfo>
    fun update(
        date: DateTime,
        basalMetabolism: Int,
        activityMetabolism: Int,
        lifeStyleList: List<LifeStyle>
    ): Flow<LifeStyleInfo>

    fun create(
        date: DateTime,
        basalMetabolism: Int,
        activityMetabolism: Int,
        lifeStyleList: List<LifeStyle>
    ): Flow<LifeStyleInfo>
}
