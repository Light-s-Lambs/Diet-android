package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository

class LoadLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        onSuccess: (LifeStyleInfo) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        lateinit var lifeStyleInfo: LifeStyleInfo
        try {
            lifeStyleInfo = repository.load(date)
            onSuccess(lifeStyleInfo)
        } catch (e: Exception) {
            onError(e)
        }
    }
}
