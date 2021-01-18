package com.example.diet.domain.lifestyle

class UserLifeStyleInfoSaveUseCase constructor(
    private val userLifeStyleInfo: UserLifeStyleInfo,
    private val repository: UserLifeStyleInfoRepository
) {
    fun save() {
        repository.save(userLifeStyleInfo.date, userLifeStyleInfo.activityMetabolism.toString())
    }
}
