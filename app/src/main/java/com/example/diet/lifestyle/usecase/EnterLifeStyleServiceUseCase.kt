package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.service.LifeStylePresentationService
import com.example.diet.lifestyle.service.UserInfoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime

class EnterLifeStyleServiceUseCase(
    private val lifeStylePresentationService: LifeStylePresentationService,
    private val userInfoService: UserInfoService,
    repository: LifeStyleRepository
) {
    val loadInDayToListUseCase = LoadLifeStyleInDayToListUseCase(repository)
    val calculateBasalMetabolismUseCase = CalculateBasalMetabolismUseCase()

    operator fun invoke(date: DateTime): Flow<Unit> = flow {
        /*
        Show 를 하려면 기초대사량, 활동 대사량, 활동 리스트가 필요함
        활동 대사량을 가져오려면 기초대사량과 활동 리스트가 필요함
        기초 대사량을 가져오려면 userInfoService.getCurrentUserInfo()가 필요함
        Load해서 리스트를 가져옴
         */
        loadInDayToListUseCase(date).collect { lifeStyleList ->
            userInfoService.getCurrentUserInfo().collect {
                calculateBasalMetabolismUseCase(
                    it.weight,
                    it.height,
                    it.age,
                    it.gender
                ).collect { basalMetabolism ->
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
}
