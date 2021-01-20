package com.example.diet.domain.lifestyle.usecase

import com.example.diet.domain.lifestyle.UserLifeStyleInfo
import com.example.diet.domain.lifestyle.UserLifeStyleInfoRepository

class UserLifeStyleInfoLoadUseCase {
    operator fun invoke(
        date: String,
        repository: UserLifeStyleInfoRepository
    ): UserLifeStyleInfo {
        return repository.load(date)
    }
}
