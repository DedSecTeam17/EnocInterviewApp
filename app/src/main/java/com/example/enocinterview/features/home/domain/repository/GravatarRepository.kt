package com.example.enocinterview.features.home.domain.repository

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.home.data.model.GravatarProfile
import kotlinx.coroutines.flow.Flow

interface GravatarRepository {
    suspend fun getGravatarProfile(profileIdentifier: String): Flow<Resource<GravatarProfile>>
}