package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.DataNoExistException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class UpdateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        lifeStyleInfo: LifeStyleInfo
    ) = flow<Boolean> {
        try {
            repository.update(date, lifeStyleInfo).collect {
                println("Update Success!")
                emit(true)
            }
        } catch (e: Throwable) {
            println(e.message)
            emit(false)
        }
    }

    fun occurDataNoExistException() = flow<Unit> {
        throw DataNoExistException("Data Doesn't Exist. Use Create instead.")
    }

    fun occurUnexpectedBehaviorException() = flow<Unit> {
        throw UnexpectedBehaviorException("Update Failed. Something weired happened.")
    }
}
