package com.example.diet.lifestyle.service

import com.example.diet.lifestyle.model.LifeStyle
import kotlinx.coroutines.flow.Flow

interface LifeStylePresentationService {
    fun showUserLifeStyleWithMetabolism(
        basalMetabolismFlow: Flow<Double>,
        activityMetabolismFlow: Flow<Double>,
        lifeStyleList: Flow<List<LifeStyle>>
    ): Flow<Unit>
}
