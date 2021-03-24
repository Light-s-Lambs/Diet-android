package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.NoMatchDataException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LoadLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
    ): Flow<LifeStyleInfo> = flow {
        try {
            repository.load(date).collect {
                println("Load Success!")
                emit(it)
            }
        } catch (e: Throwable) {
            println(e.message)
            val emptyLifeStyleInfo = LifeStyleInfo(0, 0, emptyList())
            emit(emptyLifeStyleInfo)
        }
    }

    fun occurNoMatchDataException() = flow<LifeStyleInfo> {
        throw NoMatchDataException("Load Failed. There is No Match Data.")
    }

    fun occurUnexpectedBehaviorException() = flow<LifeStyleInfo> {
        throw UnexpectedBehaviorException("Load Failed. Something weired happened.")
    }
}
