package com.example.diet.lifestyle.usecase

import com.example.diet.extension.timeout
import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.repository.UserBodyInfoRepository
import com.example.diet.lifestyle.service.LifeStylePresentationService
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime

@kotlinx.coroutines.FlowPreview
class EnterLifeStyleServiceUseCase(
    private val lifeStylePresentationService: LifeStylePresentationService,
    private val userBodyInfoRepository: UserBodyInfoRepository,
    private val loadInDayToListUseCase: LoadLifeStyleInDayToListUseCase,
    private val calculateBasalMetabolismUseCase: CalculateBasalMetabolismUseCase
) {
    private val basalMetabolismFlow: Flow<Double> = flow {
        val result = userBodyInfoRepository.getCurrentUserBodyInfo()
            .timeout(2000)
            .catch { cause ->
                throw cause
            }.flatMapConcat { userBodyInfo ->
                calculateBasalMetabolismUseCase(
                    userBodyInfo.weight,
                    userBodyInfo.height,
                    userBodyInfo.age,
                    userBodyInfo.gender
                )
            }.catch { cause ->
                throw cause
            }.first()
        emit(result)
    }

    operator fun invoke(date: DateTime): Flow<Boolean> =
        loadInDayToListUseCase(date)
            .timeout(1000)
            .flatMapConcat { lifeStyleList ->
                val basalMetabolism = basalMetabolismFlow.first()
                val activityMetabolism =
                    calculateActivityMetabolism(lifeStyleList, basalMetabolism).first()
                lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                    basalMetabolism,
                    activityMetabolism,
                    lifeStyleList
                )
            }

    private fun calculateActivityMetabolism(
        lifeStyleList: List<LifeStyle>,
        basalMetabolism: Double
    ): Flow<Double> = flow {
        emit(
            basalMetabolism + lifeStyleList.sumOf {
                it.burnedCalorie
            }
        )
    }
}
