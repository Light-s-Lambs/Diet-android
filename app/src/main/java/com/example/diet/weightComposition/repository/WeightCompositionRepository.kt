package com.example.diet.weightComposition.repository

import com.example.diet.weightComposition.model.WeightComposition
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface WeightCompositionRepository {
    fun createWeightComposition(date: DateTime, weightComposition: WeightComposition): Flow<WeightComposition>
    fun loadWeightInfo(date: DateTime): Flow<WeightComposition>
    fun updateWeightInfo(date: DateTime, weightCompositionFrom: WeightComposition, weightCompositionTo: WeightComposition): Flow<WeightComposition>
    fun deleteWeightInfo(date: DateTime): Flow<WeightComposition>
}
