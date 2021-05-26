package com.example.diet.lifestyle.usecase

import com.example.diet.extension.timeout
import com.example.diet.lifestyle.repository.UserBodyInfoRepository
import kotlinx.coroutines.flow.*

@kotlinx.coroutines.FlowPreview
class CalculateBasalMetabolismWithUserBodyInfoUseCase(
    private val userBodyInfoRepository: UserBodyInfoRepository,
    private val calculateBasalMetabolismUseCase: CalculateBasalMetabolismUseCase
) {
    operator fun invoke(): Flow<Double> = flow {
        userBodyInfoRepository.getCurrentUserBodyInfo()
            .timeout(2000)
            .flatMapConcat { userBodyInfo ->
                calculateBasalMetabolismUseCase(
                    userBodyInfo.weight,
                    userBodyInfo.height,
                    userBodyInfo.age,
                    userBodyInfo.gender
                )
            }.collect {
                emit(it)
            }
    }
}


