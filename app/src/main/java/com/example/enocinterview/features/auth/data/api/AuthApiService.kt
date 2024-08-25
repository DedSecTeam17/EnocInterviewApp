package com.example.enocinterview.features.auth.data.api

import com.example.enocinterview.features.auth.data.model.LoginRequest
import com.example.enocinterview.features.auth.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("sessions/new")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}

