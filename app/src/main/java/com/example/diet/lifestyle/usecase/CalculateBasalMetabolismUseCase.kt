package com.example.diet.lifestyle.usecase

import com.example.diet.lifestyle.model.Gender
import kotlin.math.roundToInt

class CalculateBasalMetabolismUseCase {
    private fun roundToFirstDigit(double: Double) : Double{
        return (double * 10.0).roundToInt() / 10.0
    }

    operator fun invoke(weight: Double, height: Double, age: Int, gender: Gender): Double {
        require(weight > 0 && height > 0 && age > 0) {
            "Weight, Height, Age Must Be non-negative"
        }
        return when (gender) {
            Gender.male -> {
                val basalMetabolism: Double = 66.47 + (13.75 * weight) + (5 * height) - (6.76 * age)
                roundToFirstDigit(basalMetabolism)
            }
            Gender.female -> {
                val basalMetabolism: Double =
                    655.1 + (9.56 * weight) + (1.85 * height) - (4.68 * age)
                roundToFirstDigit(basalMetabolism)
            }
        }
    }
}
