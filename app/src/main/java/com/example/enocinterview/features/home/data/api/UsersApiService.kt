package com.example.enocinterview.features.home.data.api

import com.example.enocinterview.features.home.data.model.AvatarUpdateRequest
import com.example.enocinterview.features.home.data.model.AvatarUpdateResponse
import com.example.enocinterview.features.home.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

