package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.model.WeightInfo
import com.example.diet.weightInfo.repository.WeightInfoRepository
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

class UpdateWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(date: DateTime, weightInfo: WeightInfo): Flow<WeightInfo> =
        repository.updateWeightInfo(date, weightInfo)
}
