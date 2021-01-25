package com.example.diet.weightInfo.repository

import com.example.diet.weightInfo.model.WeightInfo

interface WeightInfoRepository {
    fun save(weightInfo: WeightInfo): Boolean
    fun load(date: String): WeightInfo
    fun update(weightInfo: WeightInfo): Boolean
    fun delete(date: String): Boolean
}
