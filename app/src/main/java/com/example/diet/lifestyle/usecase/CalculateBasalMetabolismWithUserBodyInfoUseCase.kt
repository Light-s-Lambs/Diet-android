package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.service.UserBodyInfoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class CalculateBasalMetabolismWithUserBodyInfoUseCase(
    private val userBodyInfoService: UserBodyInfoService
) {
    val calculateBasalMetabolismUseCase = CalculateBasalMetabolismUseCase()

    operator fun invoke(): Flow<Double> = flow {
        userBodyInfoService.getCurrentUserBodyInfo()
            .collect {
                calculateBasalMetabolismUseCase(
                    it.weight,
                    it.height,
                    it.age,
                    it.gender
                ).collect { basalMetabolism ->
                    emit(basalMetabolism)
                }
            }
    }
}


