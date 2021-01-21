package com.example.diet.lifestyle.domain

class UserLifeStyleInfoDeleteUseCase(
    private val repository: UserLifeStyleInfoRepository
) {
    operator fun invoke(
        date: String
    ): Boolean {
        return repository.delete(date)
    }
}
