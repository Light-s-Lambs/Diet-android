package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.service.LifeStyleService
import com.example.diet.lifestyle.service.UserInfoService
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime

class EnterLifeStyleServiceUseCase(
    private val lifeStyleService: LifeStyleService,
    private val userInfoService: UserInfoService,
    repository: LifeStyleRepository
) {
    val loadInDayToListUseCase = LoadLifeStyleInDayToListUseCase(repository)
    val calculateBasalMetabolismUseCase = CalculateBasalMetabolismUseCase()

    operator fun invoke(date: DateTime): Flow<Unit> = flow {
        val userInfo = userInfoService.getCurrentUserInfo().first()
        val basalMetabolism =
            calculateBasalMetabolismUseCase(
                userInfo.weight,
                userInfo.height,
                userInfo.age,
                userInfo.gender
            ).first()
        loadInDayToListUseCase(
            date
        ).catch { cause ->
            println(cause)
        }.collect { lifeStyleList ->
            val activityMetabolism: Double = basalMetabolism + lifeStyleList.sumOf {
                it.burnedCalorie
            }
            lifeStyleService.showUserLifeStyle(
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            ).collect()
        }
    }
}
