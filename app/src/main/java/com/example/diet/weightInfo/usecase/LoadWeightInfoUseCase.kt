package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.domain.WeightInfo
import com.example.diet.weightInfo.domain.WeightInfoRepository

class LoadWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(date: String): WeightInfo = repository.load(date)
}
