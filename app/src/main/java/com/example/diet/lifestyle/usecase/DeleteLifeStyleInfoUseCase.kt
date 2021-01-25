package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfoRepository

class DeleteLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(date: String): Boolean = repository.delete(date)
}
