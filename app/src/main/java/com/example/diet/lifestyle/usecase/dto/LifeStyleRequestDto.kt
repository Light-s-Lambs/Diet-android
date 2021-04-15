package com.example.diet.lifestyle.usecase.dto

import org.joda.time.DateTime

data class LifeStyleRequestDto(
    val date: DateTime,
    val name: String,
    val time: Double,
    val burnedCalorie: Double
)
