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
        date: String,
        onSuccess: (Boolean) -> Unit,
        onFailed: (Boolean) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                repository.delete(date).collect {
                    when (it) {
                        true -> onSuccess(it)
                        false -> onFailed(it)
                    }
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}
