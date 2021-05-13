package com.example.diet.lifestyle.service

import com.example.diet.lifestyle.model.UserBodyInfo
import kotlinx.coroutines.flow.Flow

interface UserBodyInfoService {
    fun getCurrentUserBodyInfo(): Flow<UserBodyInfo>
}
