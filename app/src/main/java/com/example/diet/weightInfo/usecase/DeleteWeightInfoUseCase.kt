package com.example.diet.weightInfo.usecase

import com.example.diet.weightInfo.model.WeightInfo
import com.example.diet.weightInfo.repository.WeightInfoRepository
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

class DeleteWeightInfoUseCase(
    private val repository: WeightInfoRepository
) {
    operator fun invoke(date: DateTime): Flow<WeightInfo> = repository.deleteWeightInfo(date)
}
