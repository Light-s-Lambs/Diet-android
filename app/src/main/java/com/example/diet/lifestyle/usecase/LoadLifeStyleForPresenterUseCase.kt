package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.UserInfo
import com.example.diet.lifestyle.presenter.LifeStylePresenter
import com.example.diet.lifestyle.repository.LifeStyleRepository
import kotlinx.coroutines.flow.*
import org.joda.time.DateTime

class LoadLifeStyleForPresenterUseCase(
    private val presenter: LifeStylePresenter,
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
            presenter.showUserLifeStyle(basalMetabolism, activityMetabolism, lifeStyleList)
        }
    }
}
