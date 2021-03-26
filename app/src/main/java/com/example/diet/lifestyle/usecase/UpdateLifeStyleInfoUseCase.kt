package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.DataNoExistException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class UpdateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        lifeStyleInfo: LifeStyleInfo
    ) = callbackFlow<Boolean> {
        try {
            repository.update(date, lifeStyleInfo).collect {
                println("Update Success!")
                offer(true)
                close()
            }
        } catch (e: Throwable) {
            println(e.cause)
            offer(false)
            close()
        }
    }

    fun occurDataNoExistException() = callbackFlow<Unit> {
        close(DataNoExistException("Data Doesn't Exist. Use Create instead."))
    }

    fun occurUnexpectedBehaviorException() = callbackFlow<Unit> {
        close(UnexpectedBehaviorException("Update Failed. Something weired happened."))
    }
}
