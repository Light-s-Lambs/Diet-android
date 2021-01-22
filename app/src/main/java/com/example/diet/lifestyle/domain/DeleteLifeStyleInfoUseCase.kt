package com.example.diet.lifestyle.domain

class DeleteLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String
    ): Boolean {
        return repository.delete(date)
    }
}
