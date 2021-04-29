package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.dto.LifeStyleResponseDto
import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.LifeStyleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime

class LoadLifeStyleForUIUseCase(
    repository: LifeStyleRepository
) {
    val loadInDayToListUseCase = LoadLifeStyleInDayToListUseCase(repository)

    operator fun invoke(date: DateTime): Flow<LifeStyleResponseDto> = flow {
        /*
        Todo :
        Calculate Basal Metabolism
         */
        val basalMetabolism: Double = 0.0
        var lifeStyleList: List<LifeStyle> = emptyList()
        loadInDayToListUseCase(
            date
        ).catch { cause ->
            println(cause)
        }.collect {
            lifeStyleList = it
        }
        val activityMetabolism: Double = basalMetabolism + lifeStyleList.sumOf {
            it.burnedCalorie
        }
        val lifeStyleResponseDto =
            LifeStyleResponseDto(basalMetabolism, activityMetabolism, lifeStyleList)
        emit(lifeStyleResponseDto)
    }
}
