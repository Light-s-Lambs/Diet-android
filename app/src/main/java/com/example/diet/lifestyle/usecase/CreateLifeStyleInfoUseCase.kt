package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.DataAlreadyExistException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class CreateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        lifeStyleInfo: LifeStyleInfo
    ) = flow<Boolean> {
        repository.create(date, lifeStyleInfo).collect {
            emit(true)
        }
    }.catch {
        println(it.message)
        emit(false)
    }

    fun occurDataAlreadyExistException(): Throwable =
        DataAlreadyExistException("Data Already Exist. Use Update instead.")

    fun occurUnexpectedBehaviorException(): Throwable =
        UnexpectedBehaviorException("Create Failed. Something weired happened.")
}
