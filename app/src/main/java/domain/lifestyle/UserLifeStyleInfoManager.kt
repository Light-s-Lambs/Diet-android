package domain.lifestyle

class UserLifeStyleInfoManager constructor(
    private val userLifeStyleInfo: UserLifeStyleInfo,
    private val repository: UserLifeStyleInfoRepository
) {
    fun save() {
        repository.save(userLifeStyleInfo.date, userLifeStyleInfo.activityMetabolism.toString())
    }

    fun delete() {
        repository.delete(userLifeStyleInfo.date)
    }
}
