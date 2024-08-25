package com.example.enocinterview.features.home.domain.usecases

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.home.data.model.GravatarProfile
import com.example.enocinterview.features.home.domain.repository.GravatarRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGravatarProfileUseCase @Inject constructor(
    private val repository: GravatarRepository
) {
    suspend operator fun invoke(profileIdentifier: String): Flow<Resource<GravatarProfile>> {
        return repository.getGravatarProfile(profileIdentifier)
    }
}