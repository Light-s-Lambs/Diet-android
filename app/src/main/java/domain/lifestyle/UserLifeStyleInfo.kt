package domain.lifestyle

import com.example.diet.ui.LifeStyle
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class UserLifeStyleInfo constructor(
    userAge: Int,
    userHeight: Float,
    userWeight: Float,
    userGender: Boolean
) {
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val currentDate: String = dateFormat.format(Date())
        val repository = UserLifeStyleInfoRepository()
    }

    private val lifeStyleList = listOf<LifeStyle>()
    private val basalMetabolism: Int = calculateBM(userAge, userHeight, userWeight, userGender)
    private var activityMetabolism: Int = calculateActivityMetabolism()

    private fun calculateBM(age: Int, height: Float, weight: Float, gender: Boolean): Int =
        when (gender) {
            true -> (66.47 + (13.75 * weight) + (5 * height) - (6.76 * age)).roundToInt()
            false -> (655.1 + (9.56 * weight) + (1.85 * height) - (4.68 * age)).roundToInt()
        }

    fun addLifeStyle(lifeStyle: LifeStyle) {
        lifeStyleList.plus(lifeStyle)
        updateActivityMetabolism(lifeStyle.burnedCalorie.toFloat().roundToInt())
    }

    private fun updateActivityMetabolism(calorie: Int) {
        activityMetabolism += calorie
    }

    private fun calculateActivityMetabolism(): Int =
        basalMetabolism + lifeStyleList.sumOf { it.burnedCalorie.toInt() }

    fun save(): Boolean = repository.save(currentDate, activityMetabolism.toString())

    fun delete(): Boolean = repository.delete(currentDate)
}
