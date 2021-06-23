package com.example.diet.lifestyle.service

import com.example.diet.lifestyle.model.LifeStyle
import kotlinx.coroutines.flow.Flow

interface LifeStylePresentationService {
    fun showUserLifeStyleWithMetabolism(
        basalMetabolism: Double,
        activityMetabolism: Double,
        lifeStyleList: List<LifeStyle>
    ): Flow<Unit>
}
