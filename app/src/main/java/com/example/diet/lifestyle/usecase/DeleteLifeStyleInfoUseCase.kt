package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.NoMatchDataException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class DeleteLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String
    ) = flow<Boolean> {
        repository.delete(date).collect {
            emit(true)
        }
    }.catch {
        println(it.message)
        emit(false)
    }

    fun occurNoMatchDataException(): Throwable =
        NoMatchDataException("Delete Failed. There is No Match Data.")

    fun occurUnexpectedBehaviorException(): Throwable =
        UnexpectedBehaviorException("Delete Failed. Something weired happened.")
}
