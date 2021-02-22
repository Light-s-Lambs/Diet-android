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
        onSuccess: (LifeStyleInfo) -> Unit,
        onFailed: (LifeStyleInfo) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                repository.load(date).collect {
                    if (it.activityMetabolism != 0 && it.basalMetabolism != 0 && it.lifeStyleList.isNotEmpty()) {
                        onSuccess(it)
                    } else {
                        onFailed(it)
                    }
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}
