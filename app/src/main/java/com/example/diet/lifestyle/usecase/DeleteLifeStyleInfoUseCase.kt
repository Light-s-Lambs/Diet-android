package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DeleteLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            kotlin.runCatching {
                repository.delete(date).collect()
            }.onFailure {
                throw it
            }
        }
    }
}
