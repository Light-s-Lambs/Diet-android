package com.example.diet.lifestyle.model

data class LifeStyleInfo(
    val date: String,
    val basalMetabolism: Int,
    val activityMetabolism: Int,
    val lifeStyleList: List<LifeStyle>
)
