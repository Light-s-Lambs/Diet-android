package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.repository.WeightInfoRepository
import org.joda.time.DateTime

class DeleteWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(date: DateTime): Boolean = repository.deleteWeightInfo(date)
}
