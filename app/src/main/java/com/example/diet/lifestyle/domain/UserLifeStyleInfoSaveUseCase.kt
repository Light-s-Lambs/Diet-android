package com.example.diet.lifestyle.domain

class UserLifeStyleInfoSaveUseCase(
    private val repository: UserLifeStyleInfoRepository
) {
    operator fun invoke(
        userLifeStyleInfo: UserLifeStyleInfo
    ): Boolean {
        return repository.save(userLifeStyleInfo)
    }
}
