package com.example.diet.weightComposition.model

import org.joda.time.DateTime

data class WeightComposition(
    val date: DateTime,
    val weight: Double,
    val percentageOfBodyFat: Double,
    val amountOfMuscle: Double
)
