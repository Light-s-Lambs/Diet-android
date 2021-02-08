package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository

class SaveLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String, lifeStyleInfo: LifeStyleInfo,
        onSuccess: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try{
            onSuccess(repository.save(date, lifeStyleInfo))
        }catch (e : Exception){
            onError(e)
        }
    }
}
