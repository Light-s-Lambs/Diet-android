package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository

class LoadLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
    ) = repository.load(date)
}
