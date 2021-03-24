package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.DataAlreadyExistException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class CreateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        lifeStyleInfo: LifeStyleInfo
    ) = flow<Boolean> {
        try {
            repository.create(date, lifeStyleInfo).cancellable().collect {
                println("Create Success!")
                emit(true)
            }
        } catch (e: Throwable) {
            println(e.message)
            emit(false)
        }
    }

    fun occurDataAlreadyExistException() = flow<Unit> {
        throw DataAlreadyExistException("Data Already Exist. Use Update instead.")
    }

    fun occurUnexpectedBehaviorException() = flow<Unit> {
        throw UnexpectedBehaviorException("Create Failed. Something weired happened.")
    }
}
