package com.example.diet.lifestyle.usecase

import org.joda.time.DateTime

data class LifeStyleRequest(
    val date: DateTime,
    val name: String,
    val time: Double,
    val burnedCalorie: Double
)
