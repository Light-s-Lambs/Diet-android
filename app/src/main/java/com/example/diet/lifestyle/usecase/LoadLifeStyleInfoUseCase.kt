package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.NoMatchDataException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LoadLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
    ): Flow<LifeStyleInfo> = flow {
        repository.load(date).collect {
            emit(it)
        }
    }.catch {
        println(it.message)
        val emptyLifeStyleInfo = LifeStyleInfo(0, 0, emptyList())
        emit(emptyLifeStyleInfo)
    }

    fun occurNoMatchDataException(): Throwable =
        NoMatchDataException("Load Failed. There is No Match Data.")

    fun occurUnexpectedBehaviorException(): Throwable =
        UnexpectedBehaviorException("Load Failed. Something weired happened.")
}
