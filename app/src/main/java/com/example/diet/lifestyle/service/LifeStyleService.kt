package com.example.diet.lifestyle.service

import com.example.diet.lifestyle.model.LifeStyle

interface LifeStyleService {
    fun showUserLifeStyle(
        basalMetabolism: Double,
        activityMetabolism: Double,
        lifeStyleList: List<LifeStyle>
    )
}
