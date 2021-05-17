package com.example.diet.weightInfo.repository

import com.example.diet.weightInfo.model.WeightInfo
import kotlinx.coroutines.flow.Flow
import org.joda.time.DateTime

interface WeightInfoRepository {
    fun createWeightInfo(date: DateTime, weightInfo: WeightInfo): Flow<WeightInfo>
    fun loadWeightInfo(date: DateTime): Flow<WeightInfo>
    fun updateWeightInfo(date: DateTime, weightInfo: WeightInfo): Flow<WeightInfo>
    fun deleteWeightInfo(date: DateTime): Flow<WeightInfo>
}
