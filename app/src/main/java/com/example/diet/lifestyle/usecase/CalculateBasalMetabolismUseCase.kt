package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.Gender
import kotlin.math.roundToInt

class CalculateBasalMetabolismUseCase {
    operator fun invoke(weight: Double, height: Double, age: Int, gender: Gender): Double {
        require(weight > 0 && height > 0 && age > 0) {
            "Weight, Height, Age Must Be non-negative"
        }
        return when (gender) {
            Gender.Male -> {
                val basalMetabolism: Double = 66.47 + (13.75 * weight) + (5 * height) - (6.76 * age)
                roundHundredthsPlace(basalMetabolism)
            }
            Gender.Female -> {
                val basalMetabolism: Double =
                    655.1 + (9.56 * weight) + (1.85 * height) - (4.68 * age)
                roundHundredthsPlace(basalMetabolism)
            }
        }
    }

    private fun roundHundredthsPlace(double: Double) : Double{
        return (double * 10.0).roundToInt() / 10.0
    }
}
