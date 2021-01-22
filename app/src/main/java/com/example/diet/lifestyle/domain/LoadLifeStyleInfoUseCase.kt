package com.example.diet.lifestyle.domain

class LoadLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String
    ): LifeStyleInfo {
        return repository.load(date)
    }
}
