package com.example.diet.weightInfo.repository

import com.example.diet.weightInfo.model.WeightInfo

interface WeightInfoRepository {
    fun createWeightInfo(date: String, weightInfo: WeightInfo): Boolean
    fun loadWeightInfo(date: String): WeightInfo
    fun updateWeightInfo(date: String, weightInfo: WeightInfo): Boolean
    fun deleteWeightInfo(date: String): Boolean
}
