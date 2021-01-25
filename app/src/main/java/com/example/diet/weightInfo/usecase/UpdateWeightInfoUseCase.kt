package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.model.WeightInfo
import com.example.diet.weightInfo.repository.WeightInfoRepository

class UpdateWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(date: String, weightInfo: WeightInfo): Boolean =
        repository.update(date, weightInfo)
}
