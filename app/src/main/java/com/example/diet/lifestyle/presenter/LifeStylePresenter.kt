package com.example.diet.lifestyle.presenter

import com.example.diet.lifestyle.model.LifeStyle

interface LifeStylePresenter {
    fun showUserLifeStyle(
        basalMetabolism: Double,
        activityMetabolism: Double,
        lifeStyleList: List<LifeStyle>
    )
}
