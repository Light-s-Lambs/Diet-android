package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.model.LifeStyleInfoRepository

class SaveLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(lifeStyleInfo: LifeStyleInfo): Boolean = repository.save(lifeStyleInfo)
}
