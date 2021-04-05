package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import org.joda.time.DateTime

class UpdateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: DateTime,
        basalMetabolism: Int,
        activityMetabolism: Int,
        lifeStyleList: List<LifeStyle>
    ) = repository.update(date, basalMetabolism, activityMetabolism, lifeStyleList)
}
