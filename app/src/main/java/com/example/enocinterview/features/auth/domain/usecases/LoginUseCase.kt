package com.example.enocinterview.features.auth.domain.usecases

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.auth.data.model.LoginResponse
import com.example.enocinterview.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<LoginResponse>> {
        return authRepository.login(email, password)
    }
}