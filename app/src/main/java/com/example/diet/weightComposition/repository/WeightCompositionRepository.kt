package com.example.diet.weightComposition.repository

import com.example.diet.weightComposition.model.WeightComposition
import com.example.diet.weightComposition.usecase.WeightCompositionRequest
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface WeightCompositionRepository {
    fun createWeightComposition(weightCompositionRequest: WeightCompositionRequest): Flow<WeightComposition>

    fun deleteWeightInfo(weightCompositionRequestFrom: WeightCompositionRequest): Flow<WeightComposition>

    fun loadWeightInfo(date: DateTime): Flow<WeightComposition>

    fun updateWeightInfo(
        weightCompositionRequestFrom: WeightCompositionRequest,
        weightCompositionRequestTo: WeightCompositionRequest
    ): Flow<WeightComposition>
}
