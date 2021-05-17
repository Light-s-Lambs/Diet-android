package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.repository.WeightInfoRepository

class DeleteWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(date: String): Boolean = repository.deleteWeightInfo(date)
}
