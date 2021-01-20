package com.example.diet.domain.lifestyle.usecase

import com.example.diet.domain.lifestyle.UserLifeStyleInfo
import com.example.diet.domain.lifestyle.UserLifeStyleInfoRepository

class UserLifeStyleInfoSaveUseCase {
    operator fun invoke(
        userLifeStyleInfo: UserLifeStyleInfo,
        repository: UserLifeStyleInfoRepository
    ): Boolean {
        return repository.save(userLifeStyleInfo)
    }
}
