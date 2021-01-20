package com.example.diet.lifestyle.domain

import com.example.diet.lifestyle.domain.UserLifeStyleInfo
import com.example.diet.lifestyle.domain.UserLifeStyleInfoRepository

class UserLifeStyleUpdateUseCase {
    operator fun invoke(
        userLifeStyleInfo: UserLifeStyleInfo,
        repository: UserLifeStyleInfoRepository
    ): Boolean {
        return repository.update(userLifeStyleInfo)
    }
}
