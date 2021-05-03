package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.math.roundToInt

class CalculateBasalMetabolismUseCase {
    operator fun invoke(weight: Double, height: Double, age: Int, gender: Gender): Flow<Double> =
        flow {
            require(weight > 0 && height > 0 && age > 0) {
                "Weight, Height, Age Must Be non-negative"
            }
            when (gender) {
                Gender.Male -> {
                    val baseHeatProduction = 66.47
                    val variationAboutWeight = 13.75
                    val variationAboutHeight = 5.0
                    val variationAboutAge = 6.76
                    val basalMetabolism: Double =
                        baseHeatProduction + (variationAboutWeight * weight) + (variationAboutHeight * height) - (variationAboutAge * age)

                    roundHundredthsPlace(
                        basalMetabolism
                    ).collect {
                        emit(it)
                    }
                }
                Gender.Female -> {
                    val baseHeatProduction = 655.1
                    val variationAboutWeight = 9.56
                    val variationAboutHeight = 1.85
                    val variationAboutAge = 4.68
                    val basalMetabolism: Double =
                        baseHeatProduction + (variationAboutWeight * weight) + (variationAboutHeight * height) - (variationAboutAge * age)

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
