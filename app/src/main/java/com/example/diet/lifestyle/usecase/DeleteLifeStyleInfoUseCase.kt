package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleInfoRepository
import com.example.diet.lifestyle.usecase.exception.NoMatchDataException
import com.example.diet.lifestyle.usecase.exception.UnexpectedBehaviorException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class DeleteLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(
        date: String
    ) = callbackFlow<Boolean> {
        try {
            repository.delete(date).collect {
                println("Delete Success!")
                offer(true)
                close()
            }
        } catch (e: Throwable) {
            println(e.cause)
            offer(false)
            close()
        }
    }

    fun occurNoMatchDataException() = callbackFlow<Unit> {
        close(NoMatchDataException("Delete Failed. There is No Match Data."))
    }

    fun occurUnexpectedBehaviorException() = callbackFlow<Unit> {
        close(UnexpectedBehaviorException("Delete Failed. Something weired happened."))
    }
}
