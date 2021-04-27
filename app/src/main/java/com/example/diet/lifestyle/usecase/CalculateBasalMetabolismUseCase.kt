package com.example.diet.lifestyle.usecase

import kotlin.math.roundToInt

class CalculateBasalMetabolismUseCase {
    operator fun invoke(weight: Double, height: Double, age: Int, male: Boolean): Double {
        require(weight > 0 && height > 0 && age > 0) {
            "Weight, Height, Age Must Be non-negative"
        }
        return when (male) {
            true -> {
                val basalMetabolism: Double = 66.47 + (13.75 * weight) + (5 * height) - (6.76 * age)
                (basalMetabolism * 10).roundToInt() / 10.0
            }
            false -> {
                val basalMetabolism: Double =
                    655.1 + (9.56 * weight) + (1.85 * height) - (4.68 * age)
                (basalMetabolism * 10).roundToInt() / 10.0
            }
        }
    }
}
