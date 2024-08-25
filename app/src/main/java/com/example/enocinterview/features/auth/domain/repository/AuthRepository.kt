package com.example.enocinterview.features.auth.domain.repository

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.auth.data.model.LoginResponse
import kotlinx.coroutines.flow.Flow

// Domain layer - AuthRepositoryImpl.kt
interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<Resource<LoginResponse>>
}