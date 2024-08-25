package com.example.enocinterview.features.home.data.repository

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.home.data.api.GravatarApiService
import com.example.enocinterview.features.home.data.model.GravatarProfile
import com.example.enocinterview.features.home.domain.repository.GravatarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class GravatarRepositoryImpl @Inject constructor(
    private val apiService: GravatarApiService
) : GravatarRepository {

    override suspend fun getGravatarProfile(profileIdentifier: String): Flow<Resource<GravatarProfile>> = flow {
        emit(Resource.Loading)
        try {
            val response: Response<GravatarProfile> = apiService.getProfile(profileIdentifier)
            if (response.isSuccessful) {
                response.body()?.let { profile ->
                    emit(Resource.Success(profile))
                } ?: emit(Resource.Error("Profile data is empty"))
            } else {
                // Handle error case based on the API response
                emit(Resource.Error("Error fetching profile: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Exception occurred: ${e.localizedMessage}"))
        }
    }
}