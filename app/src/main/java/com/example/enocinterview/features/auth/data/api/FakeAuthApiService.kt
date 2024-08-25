package com.example.enocinterview.features.auth.data.api

import com.example.enocinterview.core.utils.createMockRetrofit
import com.example.enocinterview.features.auth.data.model.LoginRequest
import com.example.enocinterview.features.auth.data.model.LoginResponse
import com.example.enocinterview.features.auth.data.repository.successJson


import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.MockRetrofit.*
import retrofit2.mock.NetworkBehavior




// Create a delegate for mocking
val delegate: BehaviorDelegate<AuthApiService> = createMockRetrofit().create(AuthApiService::class.java)

// Mock implementation of AuthApiService
class MockAuthApiService(private val delegate: BehaviorDelegate<AuthApiService>) : AuthApiService {

    override suspend fun login(request: LoginRequest): LoginResponse {
        val successResponse = Gson().fromJson(successJson, LoginResponse::class.java)
        return delegate.returningResponse(successResponse).login(request)
    }
}