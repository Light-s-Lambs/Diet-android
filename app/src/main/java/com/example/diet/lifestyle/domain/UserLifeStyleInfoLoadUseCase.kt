package com.example.diet.lifestyle.domain

import com.example.diet.lifestyle.domain.UserLifeStyleInfo
import com.example.diet.lifestyle.domain.UserLifeStyleInfoRepository

class UserLifeStyleInfoLoadUseCase {
    operator fun invoke(
        date: String,
        repository: UserLifeStyleInfoRepository
    ): UserLifeStyleInfo {
        return repository.load(date)
    }
}
