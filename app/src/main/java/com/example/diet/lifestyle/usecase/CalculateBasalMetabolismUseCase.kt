package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlin.math.roundToInt

class CalculateBasalMetabolismUseCase {
    companion object {
        private const val BASE_HEAT_PRODUCTION_MALE = 66.47
        private const val VARIATION_ABOUT_WEIGHT_MALE = 13.75
        private const val VARIATION_ABOUT_HEIGHT_MALE = 5.0
        private const val VARIATION_ABOUT_AGE_MALE = 6.76
        private const val BASE_HEAT_PRODUCTION_FEMALE = 655.1
        private const val VARIATION_ABOUT_WEIGHT_FEMALE = 9.56
        private const val VARIATION_ABOUT_HEIGHT_FEMALE = 1.85
        private const val VARIATION_ABOUT_AGE_FEMALE = 4.68
    }

    operator fun invoke(weight: Double, height: Double, age: Int, gender: Gender): Flow<Double> =
        flow {
            require(weight > 0 && height > 0 && age > 0) {
                "Weight, Height, Age Must Be non-negative"
            }

            when (gender) {
                Gender.Male -> {
                    val basalMetabolism: Double =
                        BASE_HEAT_PRODUCTION_MALE + (VARIATION_ABOUT_WEIGHT_MALE * weight) + (VARIATION_ABOUT_HEIGHT_MALE * height) - (VARIATION_ABOUT_AGE_MALE * age)

                    roundHundredthsPlace(
                        basalMetabolism
                    ).collect {
                        emit(it)
                    }
                }
                Gender.Female -> {
                    val basalMetabolism: Double =
                        BASE_HEAT_PRODUCTION_FEMALE + (VARIATION_ABOUT_WEIGHT_FEMALE * weight) + (VARIATION_ABOUT_HEIGHT_FEMALE * height) - (VARIATION_ABOUT_AGE_FEMALE * age)

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
