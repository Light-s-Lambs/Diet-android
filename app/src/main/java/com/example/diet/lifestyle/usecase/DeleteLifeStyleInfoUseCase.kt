package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
    ): Flow<Boolean> {
        return flow {
            val result = repository.delete(date)
            emit(result)
        }
    }
}
