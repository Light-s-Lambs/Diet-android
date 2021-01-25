package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.model.LifeStyleInfoRepository

class UpdateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(lifeStyleInfo: LifeStyleInfo): Boolean = repository.update(lifeStyleInfo)
}
