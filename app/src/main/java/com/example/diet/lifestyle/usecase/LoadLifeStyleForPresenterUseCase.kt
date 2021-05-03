package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.presenter.LifeStylePresenter
import com.example.diet.lifestyle.repository.LifeStyleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime

class LoadLifeStyleForPresenterUseCase(
    private val presenter: LifeStylePresenter,
    repository: LifeStyleRepository
) {
    val loadInDayToListUseCase = LoadLifeStyleInDayToListUseCase(repository)

    operator fun invoke(date: DateTime): Flow<Unit> = flow {
        /*
        Todo :
        Calculate Basal Metabolism
         */
        val basalMetabolism = 0.0
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
