package com.example.diet.domain.lifestyle.usecase

import com.example.diet.domain.lifestyle.UserLifeStyleInfo
import com.example.diet.domain.lifestyle.UserLifeStyleInfoRepository

class UserLifeStyleInfoLoadUseCase constructor(
    private val date: String,
    private val repository: UserLifeStyleInfoRepository
) {
    fun load(): UserLifeStyleInfo {
        return repository.load(date)
    }
}
