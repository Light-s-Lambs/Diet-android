package com.example.diet.lifestyle.service

import com.example.diet.lifestyle.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserInfoService {
    fun getCurrentUserInfo(): Flow<UserInfo>
}
