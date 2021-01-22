package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.domain.WeightInfoRepository

class DeleteWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(date: String): Boolean = repository.delete(date)
}
