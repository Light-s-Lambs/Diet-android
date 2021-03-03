package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoadLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        onSuccess: (LifeStyleInfo) -> (Unit)
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            lateinit var lifeStyleInfo: LifeStyleInfo
            kotlin.runCatching {
                repository.load(date).collect{
                    lifeStyleInfo = it
                }
            }.onSuccess {
                onSuccess.invoke(lifeStyleInfo)
            }.onFailure {
                throw it
            }
        }
    }
}
