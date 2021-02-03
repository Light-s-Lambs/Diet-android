package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.LifeStyle
import com.example.diet.lifestyle.model.LifeStyleInfo
import com.example.diet.lifestyle.repository.LifeStyleInfoRepository

class LoadLifeStyleInfoUseCase(
    private val repository: LifeStyleInfoRepository
) {
    operator fun invoke(date: String): LifeStyleInfo {
        lateinit var lifeStyleInfo: LifeStyleInfo
        try {
            lifeStyleInfo = repository.load(date)
        } catch (e: Exception) {
            val lifeStyleList = emptyList<LifeStyle>()
            lifeStyleInfo = LifeStyleInfo(0, 0, lifeStyleList)
        } finally {
            return lifeStyleInfo
        }
    }
}
