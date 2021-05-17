package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.model.WeightInfo
import com.example.diet.weightInfo.repository.WeightInfoRepository
import org.joda.time.DateTime

class LoadWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(date: DateTime): WeightInfo = repository.loadWeightInfo(date)
}
