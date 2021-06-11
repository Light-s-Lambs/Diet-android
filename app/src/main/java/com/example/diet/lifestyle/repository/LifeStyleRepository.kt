package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.usecase.LifeStyleRequest
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface LifeStyleRepository {
    fun loadLifeStyleInDayToList(date: DateTime): Flow<List<LifeStyle>>

    fun deleteLifeStyle(
        lifeStyleRequest: LifeStyleRequest
    ): Flow<LifeStyle>

    fun updateLifeStyle(
        from: LifeStyleRequest,
        to: LifeStyleRequest
    ): Flow<LifeStyle>

    fun createLifeStyle(
        lifeStyleRequest: LifeStyleRequest
    ): Flow<LifeStyle>
}
