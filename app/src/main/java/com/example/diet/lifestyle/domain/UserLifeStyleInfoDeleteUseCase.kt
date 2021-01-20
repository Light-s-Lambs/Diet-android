package com.example.diet.lifestyle.domain

import com.example.diet.lifestyle.domain.UserLifeStyleInfoRepository

class UserLifeStyleInfoDeleteUseCase {
    operator fun invoke(
        date: String,
        repository: UserLifeStyleInfoRepository
    ): Boolean {
        return repository.delete(date)
    }
}
