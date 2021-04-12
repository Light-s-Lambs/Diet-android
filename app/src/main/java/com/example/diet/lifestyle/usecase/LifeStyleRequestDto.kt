package com.example.diet.lifestyle.usecase

import org.joda.time.DateTime

data class LifeStyleRequestDto(
    val date: DateTime,
    val name: String,
    val time: String,
    val burnedCalorie: String
)
