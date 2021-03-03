package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SaveLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        lifeStyleInfo: LifeStyleInfo
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            kotlin.runCatching {
                repository.save(date, lifeStyleInfo)
            }.onFailure {
                throw it
            }
        }
    }
}
