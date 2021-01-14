package domain.lifestyle

interface UserLifeStyleInfoRepository {
    fun save(date: String, activityMetabolism: String): Boolean
    fun delete(date: String): Boolean
}
