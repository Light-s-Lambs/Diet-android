package com.example.diet.weightInfo.repository

import com.example.diet.weightInfo.model.WeightInfo
import org.joda.time.DateTime

interface WeightInfoRepository {
    fun createWeightInfo(date: DateTime, weightInfo: WeightInfo): Boolean
    fun loadWeightInfo(date: DateTime): WeightInfo
    fun updateWeightInfo(date: DateTime, weightInfo: WeightInfo): Boolean
    fun deleteWeightInfo(date: DateTime): Boolean
}
