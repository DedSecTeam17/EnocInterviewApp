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
import retrofit2.Retrofit
import retrofit2.Retrofit.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import retrofit2.mock.BehaviorDelegate

interface UserService {
    // GET /users/:userid
    @GET("users/{userid}")
    suspend fun getUser(@Path("userid") userId: String): Response<UserResponse>

    // POST /users/:userid/avatar
    @POST("users/{userid}/avatar")
    suspend fun updateAvatar(
        @Path("userid") userId: String,
        @Body request: AvatarUpdateRequest
    ): Response<AvatarUpdateResponse>
}

