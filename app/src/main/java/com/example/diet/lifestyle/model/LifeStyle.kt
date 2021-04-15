package com.example.diet.lifestyle.model

import org.joda.time.DateTime

data class LifeStyle(
    val date: DateTime,
    val name: String,
    val time: Double,
    val burnedCalorie: Double
)
