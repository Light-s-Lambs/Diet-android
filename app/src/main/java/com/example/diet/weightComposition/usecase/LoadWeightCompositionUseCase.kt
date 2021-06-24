package com.example.diet.weightComposition.usecase

import com.example.diet.weightComposition.model.WeightComposition
import com.example.diet.weightComposition.repository.WeightCompositionRepository
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

class LoadWeightCompositionUseCase(
    private val repository: WeightCompositionRepository
) {
    operator fun invoke(date: DateTime): Flow<WeightComposition> = repository.loadWeightInfo(date)
}
