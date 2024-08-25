package com.example.enocinterview.features.home.domain.usecases

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.home.data.model.UserResponse
import com.example.enocinterview.features.home.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: String): Flow<Resource<UserResponse>> {
        return userRepository.getUser(userId)
    }
}