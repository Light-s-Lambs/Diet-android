package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.UserInfo
import com.example.diet.lifestyle.repository.LifeStyleRepository
import com.example.diet.lifestyle.service.LifeStyleService
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime

class EnterLifeStyleServiceUseCase(
    private val service: LifeStyleService,
    private val userInfo: UserInfo,
    repository: LifeStyleRepository
) {
    val loadInDayToListUseCase = LoadLifeStyleInDayToListUseCase(repository)
    val calculateBasalMetabolismUseCase = CalculateBasalMetabolismUseCase()

    operator fun invoke(date: DateTime): Flow<Unit> = flow {
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
            service.showUserLifeStyle(
                basalMetabolism,
                activityMetabolism,
                lifeStyleList
            ).collect()
        }
    }
}
