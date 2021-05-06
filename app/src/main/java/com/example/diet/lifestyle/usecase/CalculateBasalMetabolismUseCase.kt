package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.math.roundToInt

class CalculateBasalMetabolismUseCase {
    companion object {
        private const val baseHeatProductionMale = 66.47
        private const val variationAboutWeightMale = 13.75
        private const val variationAboutHeightMale = 5.0
        private const val variationAboutAgeMale = 6.76
        private const val baseHeatProductionFemale = 655.1
        private const val variationAboutWeightFemale = 9.56
        private const val variationAboutHeightFemale = 1.85
        private const val variationAboutAgeFemale = 4.68
    }

    operator fun invoke(weight: Double, height: Double, age: Int, gender: Gender): Flow<Double> =
        flow {
            require(weight > 0 && height > 0 && age > 0) {
                "Weight, Height, Age Must Be non-negative"
            }

            when (gender) {
                Gender.Male -> {
                    val basalMetabolism: Double =
                        baseHeatProductionMale + (variationAboutWeightMale * weight) + (variationAboutHeightMale * height) - (variationAboutAgeMale * age)

                    roundHundredthsPlace(
                        basalMetabolism
                    ).collect {
                        emit(it)
                    }
                }
                Gender.Female -> {
                    val basalMetabolism: Double =
                        baseHeatProductionFemale + (variationAboutWeightFemale * weight) + (variationAboutHeightFemale * height) - (variationAboutAgeFemale * age)

                    roundHundredthsPlace(
                        basalMetabolism
                    ).collect {
                        emit(it)
                    }
                }
            }
        }

    private fun roundHundredthsPlace(double: Double): Flow<Double> = flow {
        emit(
            (double * 10.0).roundToInt() / 10.0
        )
    }
}
