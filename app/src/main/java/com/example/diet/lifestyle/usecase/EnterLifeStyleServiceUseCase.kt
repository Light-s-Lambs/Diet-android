package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.service.LifeStylePresentationService
import com.example.diet.lifestyle.service.UserInfoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import org.joda.time.DateTime

@kotlinx.coroutines.FlowPreview
class EnterLifeStyleServiceUseCase(
    private val lifeStylePresentationService: LifeStylePresentationService,
    private val userInfoService: UserInfoService,
    private val loadInDayToListUseCase: LoadLifeStyleInDayToListUseCase,
    private val calculateBasalMetabolismUseCase: CalculateBasalMetabolismUseCase
) {
    /*
    Show 를 하려면 기초대사량, 활동 대사량, 활동 리스트가 필요함
    활동 대사량을 가져오려면 기초대사량과 활동 리스트가 필요함
    기초 대사량을 가져오려면 userInfoService.getCurrentUserInfo()가 필요함
    Load해서 리스트를 가져옴
 */
    operator fun invoke(date: DateTime): Flow<Unit> =
        loadInDayToListUseCase(date)
            .flatMapConcat { lifeStyleList ->
                userInfoService.getCurrentUserInfo()
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
