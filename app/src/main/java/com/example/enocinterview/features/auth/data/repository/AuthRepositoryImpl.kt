package com.example.enocinterview.features.auth.data.repository

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.auth.data.api.AuthApiService
import com.example.enocinterview.features.auth.data.model.LoginRequest
import com.example.enocinterview.features.auth.data.model.LoginResponse
import com.example.enocinterview.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthRepository {

    override suspend fun login(email: String, password: String): Flow<Resource<LoginResponse>> = flow {
        emit(Resource.Loading)
        try {
            // Simulate network delay
//            delay(100)
            val response = authApiService.login(LoginRequest(email, password))
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Login failed"))
        }
    }
}