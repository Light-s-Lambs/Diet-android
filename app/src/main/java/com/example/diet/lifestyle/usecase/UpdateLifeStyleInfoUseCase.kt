package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.domain.LifeStyleInfo
import com.example.diet.lifestyle.domain.LifeStyleInfoRepository

class UpdateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        lifeStyleInfo: LifeStyleInfo
    ): Boolean {
        return repository.update(lifeStyleInfo)
    }
}
