package com.example.diet.lifestyle.repository

import com.example.diet.lifestyle.model.UserBodyInfo
import kotlinx.coroutines.flow.Flow

interface UserBodyInfoRepository {
    fun getCurrentUserBodyInfo(): Flow<UserBodyInfo>
}
