package com.example.diet.lifestyle.domain

class SaveLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        lifeStyleInfo: LifeStyleInfo
    ): Boolean {
        return repository.save(lifeStyleInfo)
    }
}
