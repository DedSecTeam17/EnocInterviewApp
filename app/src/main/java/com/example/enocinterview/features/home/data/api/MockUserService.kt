package com.example.enocinterview.features.home.data.api

import com.example.enocinterview.core.utils.createMockRetrofit
import com.example.enocinterview.features.auth.data.api.AuthApiService
import com.example.enocinterview.features.auth.data.model.LoginRequest
import com.example.enocinterview.features.auth.data.model.LoginResponse
import com.example.enocinterview.features.auth.data.repository.successJson
import com.example.enocinterview.features.home.data.model.AvatarUpdateRequest
import com.example.enocinterview.features.home.data.model.AvatarUpdateResponse
import com.example.enocinterview.features.home.data.model.UserResponse
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate


val delegate: BehaviorDelegate<UserService> = createMockRetrofit().create(UserService::class.java)

// Mock implementation of AuthApiService
class MockUserService(private val delegate: BehaviorDelegate<UserService>) : UserService {

    override suspend fun getUser(userId: String): Response<UserResponse> {
        val mockedResponse = UserResponse(email = "melamin100@yahoo.com", avatar_url = "https://via.placeholder.com/600/1ee8a4")
        return delegate.returningResponse(mockedResponse).getUser(userId = userId)
    }

    override suspend fun updateAvatar(
        userId: String,
        request: AvatarUpdateRequest
    ): Response<AvatarUpdateResponse> {
        val mockedResponse = AvatarUpdateResponse(avatar_url = "https://via.placeholder.com/600/1ee8a4")
        return delegate.returningResponse(mockedResponse).updateAvatar(userId = userId, request = request)
    }
}