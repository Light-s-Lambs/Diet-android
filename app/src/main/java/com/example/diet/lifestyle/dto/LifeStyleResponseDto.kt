package com.example.diet.lifestyle.dto

import com.example.diet.lifestyle.model.LifeStyle

data class LifeStyleResponseDto(
    val basalMetabolism: Double,
    val activityMetabolism: Double,
    val lifeStyleList: List<LifeStyle>
)
