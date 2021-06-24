package com.example.diet.weightComposition.usecase

import com.example.diet.weightComposition.repository.WeightCompositionRepository

class UpdateWeightCompositionUseCase(
    private val repository: WeightCompositionRepository
) {
    operator fun invoke(
        weightCompositionRequestFrom: WeightCompositionRequest,
        weightCompositionRequestTo: WeightCompositionRequest
    ) = repository.updateWeightInfo(weightCompositionRequestFrom, weightCompositionRequestTo)
}
