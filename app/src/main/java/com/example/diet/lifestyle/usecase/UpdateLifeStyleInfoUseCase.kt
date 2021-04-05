package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository

class UpdateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        basalMetabolism: Int,
        activityMetabolism: Int,
        lifeStyleList: List<LifeStyle>
    ) = repository.update(date, basalMetabolism, activityMetabolism, lifeStyleList)
}
