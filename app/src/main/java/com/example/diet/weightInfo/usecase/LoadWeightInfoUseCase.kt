package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.model.WeightInfo
import com.example.diet.weightInfo.repository.WeightInfoRepository

class LoadWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(date: String): WeightInfo = repository.load(date)
}
