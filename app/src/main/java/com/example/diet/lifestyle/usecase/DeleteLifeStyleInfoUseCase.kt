package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.NoMatchDataException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class DeleteLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String
    ) = flow<Boolean> {
        try {
            repository.delete(date).collect {
                println("Delete Success!")
                emit(true)
            }
        } catch (e: Throwable) {
            println(e.message)
            emit(false)
        }
    }

    fun occurNoMatchDataException() = flow<Unit> {
        throw NoMatchDataException("Delete Failed. There is No Match Data.")
    }

    fun occurUnexpectedBehaviorException() = flow<Unit> {
        throw UnexpectedBehaviorException("Delete Failed. Something weired happened.")
    }
}
