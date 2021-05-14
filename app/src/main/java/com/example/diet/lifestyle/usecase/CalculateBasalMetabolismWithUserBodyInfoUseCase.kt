package com.example.diet.lifestyle.usecase

import com.example.diet.extension.timeout
import com.example.diet.lifestyle.service.UserBodyInfoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat

@kotlinx.coroutines.FlowPreview
class CalculateBasalMetabolismWithUserBodyInfoUseCase(
    private val userBodyInfoService: UserBodyInfoService
) {
    val calculateBasalMetabolismUseCase = CalculateBasalMetabolismUseCase()

    suspend operator fun invoke(): Flow<Double> =
        userBodyInfoService.getCurrentUserBodyInfo()
            .timeout(2000)
            .flatMapConcat { userBodyInfo ->
                calculateBasalMetabolismUseCase(
                    userBodyInfo.weight,
                    userBodyInfo.height,
                    userBodyInfo.age,
                    userBodyInfo.gender
                )
            }
}


