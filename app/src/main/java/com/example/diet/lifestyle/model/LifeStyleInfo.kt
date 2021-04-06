package com.example.diet.lifestyle.model

import org.joda.time.DateTime

data class LifeStyleInfo(
    val date: DateTime,
    val lifeStyleList: List<LifeStyle>
)
