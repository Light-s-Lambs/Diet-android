package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        lifeStyleInfo: LifeStyleInfo
    ): Flow<Boolean> {
        return flow {
            val result = repository.save(date, lifeStyleInfo)
            emit(result)
        }
    }
}
