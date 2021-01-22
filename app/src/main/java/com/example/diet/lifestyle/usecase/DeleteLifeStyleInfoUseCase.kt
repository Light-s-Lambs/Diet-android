package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.domain.LifeStyleInfoRepository

class DeleteLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String
    ): Boolean {
        return repository.delete(date)
    }
}
