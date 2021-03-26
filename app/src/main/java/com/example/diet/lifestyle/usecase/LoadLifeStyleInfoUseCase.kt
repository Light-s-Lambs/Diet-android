package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.NoMatchDataException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class LoadLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
    ) = callbackFlow<LifeStyleInfo> {
        try {
            repository.load(date).collect {
                println("Load Success!")
                offer(it)
                close()
            }
        } catch (e: Throwable) {
            println(e.cause)
            val emptyLifeStyleInfo = LifeStyleInfo(0, 0, emptyList())
            offer(emptyLifeStyleInfo)
            close()
        }
    }

    fun occurNoMatchDataException() = callbackFlow<LifeStyleInfo> {
        close(NoMatchDataException("Load Failed. There is No Match Data."))
    }

    fun occurUnexpectedBehaviorException() = callbackFlow<LifeStyleInfo> {
        close(UnexpectedBehaviorException("Load Failed. Something weired happened."))
    }
}
