package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserBodyInfoRepository {
    fun getCurrentUserInfo(): Flow<UserInfo>
}
