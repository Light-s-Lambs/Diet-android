package com.example.diet.weightComposition.usecase

import com.example.diet.weightComposition.repository.WeightCompositionRepository

class SaveWeightCompositionUseCase(
    private val repository: WeightCompositionRepository
) {
    operator fun invoke(
        weightCompositionRequestFrom: WeightCompositionRequest
    ) = repository.createWeightComposition(weightCompositionRequestFrom)
}
