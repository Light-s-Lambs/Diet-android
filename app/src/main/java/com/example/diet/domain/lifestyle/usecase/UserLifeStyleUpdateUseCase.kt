package com.example.diet.domain.lifestyle.usecase

import com.example.diet.domain.lifestyle.UserLifeStyleInfo
import com.example.diet.domain.lifestyle.UserLifeStyleInfoRepository

class UserLifeStyleUpdateUseCase constructor(
    private val userLifeStyleInfo: UserLifeStyleInfo,
    private val repository: UserLifeStyleInfoRepository
) {
    fun update(): Boolean {
        return repository.update(userLifeStyleInfo)
    }
}
