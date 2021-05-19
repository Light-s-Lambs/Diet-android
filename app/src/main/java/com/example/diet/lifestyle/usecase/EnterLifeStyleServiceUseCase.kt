package com.example.diet.lifestyle.usecase

import com.example.diet.extension.timeout
import com.example.diet.lifestyle.repository.UserBodyInfoRepository
import com.example.diet.lifestyle.service.LifeStylePresentationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime

@kotlinx.coroutines.FlowPreview
class EnterLifeStyleServiceUseCase(
    private val lifeStylePresentationService: LifeStylePresentationService,
    private val userBodyInfoRepository: UserBodyInfoRepository,
    private val loadInDayToListUseCase: LoadLifeStyleInDayToListUseCase,
    private val calculateBasalMetabolismUseCase: CalculateBasalMetabolismUseCase
) {
    operator fun invoke(date: DateTime): Flow<Unit> = flow {
        loadInDayToListUseCase(date)
            .timeout(1000)
            .flatMapConcat { lifeStyleList ->
                userBodyInfoRepository.getCurrentUserInfo()
                    .timeout(2000)
                    .flatMapConcat {
                        calculateBasalMetabolismUseCase(
                            it.weight,
                            it.height,
                            it.age,
                            it.gender
                        )
                    }.flatMapConcat { basalMetabolism ->
                        val activityMetabolism: Double =
                            basalMetabolism + lifeStyleList.sumOf { lifeStyle ->
                                lifeStyle.burnedCalorie
                            }
                        lifeStylePresentationService.showUserLifeStyleWithMetabolism(
                            basalMetabolism,
                            activityMetabolism,
                            lifeStyleList
                        )
                    }
            }
    }
}
