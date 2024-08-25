package com.example.enocinterview.features.home.domain.usecases

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.home.data.model.AvatarUpdateResponse
import com.example.enocinterview.features.home.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateAvatarUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: String, avatarBase64: String): Flow<Resource<AvatarUpdateResponse>> {
        return userRepository.updateAvatar(userId, avatarBase64)
    }
}