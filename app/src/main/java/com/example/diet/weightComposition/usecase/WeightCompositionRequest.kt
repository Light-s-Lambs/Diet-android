package com.example.diet.weightComposition.usecase

import org.joda.time.DateTime

data class WeightCompositionRequest(
    val date: DateTime,
    val weight: Double,
    val percentageOfBodyFat: Double,
    val amountOfMuscle: Double
)
