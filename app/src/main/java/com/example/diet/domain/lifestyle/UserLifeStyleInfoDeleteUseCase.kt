package com.example.diet.domain.lifestyle

class UserLifeStyleInfoDeleteUseCase constructor(
    private val userLifeStyleInfo: UserLifeStyleInfo,
    private val repository: UserLifeStyleInfoRepository
) {
    fun delete() {
        repository.delete(userLifeStyleInfo.date)
    }
}
