package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.domain.LifeStyleInfo
import com.example.diet.lifestyle.domain.LifeStyleInfoRepository

class SaveLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(lifeStyleInfo: LifeStyleInfo): Boolean = repository.save(lifeStyleInfo)
}
