package domain.lifestyle

class UserLifeStyleInfoRepository : Repository {
    override fun save(date: String, activityMetabolism: String): Boolean {
        return true
    }

    override fun delete(date: String): Boolean {
        return true
    }
}
