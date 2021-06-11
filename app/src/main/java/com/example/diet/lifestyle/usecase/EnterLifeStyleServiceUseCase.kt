package com.example.diet.lifestyle.usecase

import com.example.diet.extension.timeout
import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.service.LifeStylePresentationService
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime

@kotlinx.coroutines.FlowPreview
class EnterLifeStyleServiceUseCase(
    private val lifeStylePresentationService: LifeStylePresentationService,
    private val loadInDayToListUseCase: LoadLifeStyleInDayToListUseCase,
    private val calculateBasalMetabolismUseCaseWithUserBodyInfoUseCase: CalculateBasalMetabolismWithUserBodyInfoUseCase
) {
    operator fun invoke(date: DateTime): Flow<Unit> = flow {
        val basalMetabolism = calculateBasalMetabolismUseCaseWithUserBodyInfoUseCase().first()
        val activityMetabolism = calculateActivityMetabolism(
            loadInDayToListUseCase(date)
                .timeout(1000),
            calculateBasalMetabolismUseCaseWithUserBodyInfoUseCase()
        ).first()
        val lifeStyleList = loadInDayToListUseCase(date)
            .timeout(1000)
            .first()
        lifeStylePresentationService.showUserLifeStyleWithMetabolism(
            basalMetabolism,
            activityMetabolism,
            lifeStyleList
        ).collect {
            emit(it)
        }
    }

    private fun calculateActivityMetabolism(
        lifeStyleListFlow: Flow<List<LifeStyle>>,
        basalMetabolismFlow: Flow<Double>
    ): Flow<Double> = lifeStyleListFlow
        .zip(basalMetabolismFlow) { lifeStyleList, basalMetabolism ->
            basalMetabolism + lifeStyleList.sumOf { it.burnedCalorie }
        }
}
