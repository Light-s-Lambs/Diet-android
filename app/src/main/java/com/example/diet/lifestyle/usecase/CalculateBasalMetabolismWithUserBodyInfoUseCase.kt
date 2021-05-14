package com.example.diet.lifestyle.usecase

import com.example.diet.extension.timeout
import com.example.diet.lifestyle.service.UserBodyInfoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

@kotlinx.coroutines.FlowPreview
class CalculateBasalMetabolismWithUserBodyInfoUseCase(
    private val userBodyInfoService: UserBodyInfoService
) {
    val calculateBasalMetabolismUseCase = CalculateBasalMetabolismUseCase()

    operator fun invoke(): Flow<Double> = flow {
        userBodyInfoService.getCurrentUserBodyInfo()
            .timeout(2000)
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


