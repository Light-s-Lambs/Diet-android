package com.example.diet.lifestyle.domain

import com.example.diet.lifestyle.domain.UserLifeStyleInfo
import com.example.diet.lifestyle.domain.UserLifeStyleInfoRepository

class UserLifeStyleInfoSaveUseCase {
    operator fun invoke(
        userLifeStyleInfo: UserLifeStyleInfo,
        repository: UserLifeStyleInfoRepository
    ): Boolean {
        return repository.save(userLifeStyleInfo)
    }
}
