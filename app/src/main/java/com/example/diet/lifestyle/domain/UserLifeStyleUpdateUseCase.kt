package com.example.diet.lifestyle.domain

class UserLifeStyleUpdateUseCase(
    private val repository: UserLifeStyleInfoRepository
) {
    operator fun invoke(
        userLifeStyleInfo: UserLifeStyleInfo
    ): Boolean {
        return repository.update(userLifeStyleInfo)
    }
}
