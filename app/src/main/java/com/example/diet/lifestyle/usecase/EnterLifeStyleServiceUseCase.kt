package com.example.diet.lifestyle.usecase

import com.example.diet.extension.timeout
import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.service.LifeStylePresentationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import org.joda.time.DateTime

@kotlinx.coroutines.FlowPreview
class EnterLifeStyleServiceUseCase(
    private val lifeStylePresentationService: LifeStylePresentationService,
    private val loadInDayToListUseCase: LoadLifeStyleInDayToListUseCase,
    private val calculateBasalMetabolismUseCaseWithUserBodyInfoUseCase: CalculateBasalMetabolismWithUserBodyInfoUseCase
) {
    operator fun invoke(date: DateTime): Flow<Unit> =
        lifeStylePresentationService.showUserLifeStyleWithMetabolism(
            calculateBasalMetabolismUseCaseWithUserBodyInfoUseCase(),
            calculateActivityMetabolism(
                loadInDayToListUseCase(date)
                    .timeout(1000),
                calculateBasalMetabolismUseCaseWithUserBodyInfoUseCase()
            ),
            loadInDayToListUseCase(date)
                .timeout(1000)
        )

    private fun calculateActivityMetabolism(
        lifeStyleListFlow: Flow<List<LifeStyle>>,
        basalMetabolismFlow: Flow<Double>
    ): Flow<Double> = lifeStyleListFlow
        .zip(basalMetabolismFlow) { lifeStyleList, basalMetabolism ->
            basalMetabolism + lifeStyleList.sumOf { it.burnedCalorie }
        }
}
