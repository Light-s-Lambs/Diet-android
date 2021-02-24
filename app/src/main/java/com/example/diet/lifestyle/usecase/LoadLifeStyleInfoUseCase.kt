package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
    ): Flow<LifeStyleInfo> {
        return flow {
            val result = repository.load(date)
            emit(result)
        }
    }
}
