package com.example.diet.domain.lifestyle

data class UserLifeStyleInfo(
    val date: String,
    val basalMetabolism: Int,
    val activityMetabolism: Int,
    val lifeStyleList: List<LifeStyle>
)
