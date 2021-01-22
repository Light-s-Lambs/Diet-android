package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.domain.WeightInfo
import com.example.diet.weightInfo.domain.WeightInfoRepository

class UpdateWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(weightInfo: WeightInfo): Boolean = repository.update(weightInfo)
}
