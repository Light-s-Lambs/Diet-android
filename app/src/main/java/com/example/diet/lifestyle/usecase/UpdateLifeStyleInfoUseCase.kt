package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository

class UpdateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(date: String, lifeStyleInfo: LifeStyleInfo): Boolean =
        repository.update(date, lifeStyleInfo)
}
