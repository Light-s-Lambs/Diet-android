package com.example.diet.weightInfo.domain

interface WeightInfoRepository {
    fun save(weightInfo: WeightInfo): Boolean
    fun load(date: String): WeightInfo
    fun update(weightInfo: WeightInfo): Boolean
    fun delete(date: String): Boolean
}