package com.example.diet.domain.lifestyle.usecase

import com.example.diet.domain.lifestyle.UserLifeStyleInfoRepository

class UserLifeStyleInfoDeleteUseCase constructor(
    private val date: String,
    private val repository: UserLifeStyleInfoRepository
) {
    fun delete() {
        repository.delete(date)
    }
}
