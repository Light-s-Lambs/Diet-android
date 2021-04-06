package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import org.joda.time.DateTime

class CreateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: DateTime,
        lifeStyleList: List<LifeStyle>
    ) = repository.create(date, lifeStyleList)
}
