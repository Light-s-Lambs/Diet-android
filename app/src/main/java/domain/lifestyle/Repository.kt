package domain.lifestyle

interface Repository {
    fun save(date: String, activityMetabolism: String): Boolean
    fun delete(date: String): Boolean
}
