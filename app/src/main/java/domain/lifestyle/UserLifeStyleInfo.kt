package domain.lifestyle

import com.example.diet.ui.LifeStyle

data class UserLifeStyleInfo(
    val date: String,
    val basalMetabolism: Int,
    val activityMetabolism: Int,
    val lifeStyleList: List<LifeStyle>
)
