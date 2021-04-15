package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.repository.LifeStyleRepository
import org.joda.time.DateTime

class LoadLifeStyleInDayToListUseCase(
    private val repository: LifeStyleRepository
) {
    operator fun invoke(
        date: DateTime,
    ) = repository.loadLifeStyleInDayToList(date)
}
