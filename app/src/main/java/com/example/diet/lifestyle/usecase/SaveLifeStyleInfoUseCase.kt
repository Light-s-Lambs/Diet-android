package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.DataNoExistException
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
                repository.update(date,lifeStyleInfo)
            }.onFailure {
                when(it){
                    is DataNoExistException ->{
                        repository.create(date,lifeStyleInfo)
                    }
                    else -> throw it
                }
            }
        }
    }
}
