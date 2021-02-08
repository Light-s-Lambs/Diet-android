package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository

class DeleteLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        onSuccess: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            onSuccess(repository.delete(date))
        } catch (e: Exception) {
            onError(e)
        }
    }
}
