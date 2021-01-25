package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.model.WeightInfo
import com.example.diet.weightInfo.repository.WeightInfoRepository

class SaveWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(date: String, weightInfo: WeightInfo): Boolean =
        repository.save(date, weightInfo)
}
