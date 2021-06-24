package com.example.diet.weightComposition.usecase

import com.example.diet.weightComposition.repository.WeightCompositionRepository
import org.joda.time.DateTime

class LoadWeightCompositionUseCase(
    private val repository: WeightCompositionRepository
) {
    operator fun invoke(
        date: DateTime
    ) = repository.loadWeightInfo(date)
}
