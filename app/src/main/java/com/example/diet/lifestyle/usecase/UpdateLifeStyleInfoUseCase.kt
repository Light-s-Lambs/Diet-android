package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.DataNoExistException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class UpdateLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String,
        lifeStyleInfo: LifeStyleInfo
    ) = flow<Boolean> {
        repository.update(date, lifeStyleInfo).collect {
            emit(true)
        }
    }.catch {
        println(it.message)
        emit(false)
    }

    fun occurDataNoExistException(): Throwable =
        DataNoExistException("Data Doesn't Exist. Use Create instead.")

    fun occurUnexpectedBehaviorException(): Throwable =
        UnexpectedBehaviorException("Update Failed. Something weired happened.")
}
