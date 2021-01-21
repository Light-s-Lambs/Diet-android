package com.example.diet.lifestyle.domain

class UserLifeStyleInfoLoadUseCase(
    private val repository: UserLifeStyleInfoRepository
) {
    operator fun invoke(
        date: String
    ): UserLifeStyleInfo {
        return repository.load(date)
    }
}
