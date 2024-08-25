package com.example.enocinterview.features.home.domain.repository

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.home.data.model.AvatarUpdateResponse
import com.example.enocinterview.features.home.data.model.UserResponse
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(userId: String): Flow<Resource<UserResponse>>
    fun updateAvatar(userId: String, avatarBase64: String): Flow<Resource<AvatarUpdateResponse>>
}