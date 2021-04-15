package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.usecase.dto.LifeStyleRequestDto
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface LifeStyleRepository {
    fun loadLifeStyleInDayToList(date: DateTime): Flow<List<LifeStyle>>

    fun deleteLifeStyle(
        lifeStyleRequestDto: LifeStyleRequestDto
    ): Flow<LifeStyle>

    fun updateLifeStyle(
        from: LifeStyleRequestDto,
        to: LifeStyleRequestDto
    ): Flow<LifeStyle>

    fun createLifeStyle(
        lifeStyleRequestDto: LifeStyleRequestDto
    ): Flow<LifeStyle>
}
