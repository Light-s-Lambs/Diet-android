package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.domain.LifeStyleInfo
import com.example.diet.lifestyle.domain.LifeStyleInfoRepository

class LoadLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(date: String): LifeStyleInfo = repository.load(date)

}
