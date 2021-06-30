package com.example.diet.lifestyle.usecase

import com.example.diet.extension.timeout
import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.service.LifeStylePresentationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.zip
import org.joda.time.DateTime

@kotlinx.coroutines.FlowPreview
class EnterLifeStyleServiceUseCase(
    private val lifeStylePresentationService: LifeStylePresentationService,
    private val loadInDayToListUseCase: LoadLifeStyleInDayToListUseCase,
    private val calculateBasalMetabolismWithUserBodyInfoUseCase: CalculateBasalMetabolismWithUserBodyInfoUseCase
) {
    operator fun invoke(date: DateTime): Flow<Unit> =
        calculateBasalMetabolismWithUserBodyInfoUseCase().zip(
            loadInDayToListUseCase(date).timeout(1000)
        ) { basalMetabolism, lifeStyleList ->
            LifeStylePresentationData(
                basalMetabolism,
                calculateActivityMetabolism(
                    lifeStyleList,
                    basalMetabolism
                ),
                lifeStyleList
            )
        }.flatMapConcat {
            lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                it.basalMetabolism,
                it.activityMetabolism,
                it.lifeStyleList
            )
        }

    private fun calculateActivityMetabolism(
        lifeStyleList: List<LifeStyle>,
        basalMetabolism: Double
    ): Double {
        return lifeStyleList.fold(basalMetabolism) { activityMetabolism, lifeStyle -> activityMetabolism + lifeStyle.burnedCalorie }
    }

    data class LifeStylePresentationData(
        val basalMetabolism: Double,
        val activityMetabolism: Double,
        val lifeStyleList: List<LifeStyle>
    )
}
