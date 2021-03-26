package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.DataAlreadyExistException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class CreateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        lifeStyleInfo: LifeStyleInfo
    ) = callbackFlow<Boolean> {
        try {
            repository.create(date, lifeStyleInfo).collect {
                println("Create Success!")
                offer(true)
                close()
            }
        } catch (e: Throwable) {
            println(e.cause)
            offer(false)
            close()
        }
    }

    fun occurDataAlreadyExistException() = callbackFlow<Unit> {
        close(DataAlreadyExistException("Data Already Exist. Use Update instead."))
    }

    fun occurUnexpectedBehaviorException() = callbackFlow<Unit> {
        close(UnexpectedBehaviorException("Create Failed. Something weired happened."))
    }
}
