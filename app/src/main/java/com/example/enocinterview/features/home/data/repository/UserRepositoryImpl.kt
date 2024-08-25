package com.example.enocinterview.features.home.data.repository

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.home.data.api.UserService
import com.example.enocinterview.features.home.data.model.AvatarUpdateRequest
import com.example.enocinterview.features.home.data.model.AvatarUpdateResponse
import com.example.enocinterview.features.home.data.model.UserResponse
import com.example.enocinterview.features.home.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override fun getUser(userId: String): Flow<Resource<UserResponse>> = flow {
        emit(Resource.Loading)
        try {
            val response = userService.getUser(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("User not found"))
            } else {
                emit(Resource.Error("Error fetching user"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Exception occurred: ${e.localizedMessage}"))
        }
    }

    override fun updateAvatar(userId: String, avatarBase64: String): Flow<Resource<AvatarUpdateResponse>> = flow {
        emit(Resource.Loading)
        try {
            val response = userService.updateAvatar(userId, AvatarUpdateRequest(avatarBase64))
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(Resource.Success(it))
                } ?: emit(Resource.Error("Error updating avatar"))
            } else {
                emit(Resource.Error("Error updating avatar"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Exception occurred: ${e.localizedMessage}"))
        }
    }
}