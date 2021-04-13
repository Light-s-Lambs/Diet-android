package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.usecase.dto.LifeStyleRequestDto
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface LifeStyleRepository {
    fun loadInDayToList(date: DateTime): Flow<List<LifeStyle>>

    fun delete(
        lifeStyleRequestDto: LifeStyleRequestDto
    ): Flow<LifeStyle>

    fun update(
        from: LifeStyleRequestDto,
        to: LifeStyleRequestDto
    ): Flow<LifeStyle>

    fun create(
        lifeStyleRequestDto: LifeStyleRequestDto
    ): Flow<LifeStyle>
}
