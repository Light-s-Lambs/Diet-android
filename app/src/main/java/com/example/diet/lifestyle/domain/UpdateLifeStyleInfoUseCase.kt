package com.example.diet.lifestyle.domain

class UpdateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        lifeStyleInfo: LifeStyleInfo
    ): Boolean {
        return repository.update(lifeStyleInfo)
    }
}
