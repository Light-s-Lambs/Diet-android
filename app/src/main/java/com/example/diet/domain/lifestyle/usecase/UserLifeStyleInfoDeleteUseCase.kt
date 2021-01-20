package com.example.diet.domain.lifestyle.usecase

import com.example.diet.domain.lifestyle.UserLifeStyleInfoRepository

class UserLifeStyleInfoDeleteUseCase {
    operator fun invoke(
        date: String,
        repository: UserLifeStyleInfoRepository
    ): Boolean {
        return repository.delete(date)
    }
}
