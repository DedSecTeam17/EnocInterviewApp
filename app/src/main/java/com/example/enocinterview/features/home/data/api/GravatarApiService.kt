package com.example.enocinterview.features.home.data.api
import com.example.enocinterview.features.home.data.model.GravatarProfile
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GravatarApiService {
    @GET("/v3/profiles/{profileIdentifier}")
    suspend fun getProfile(@Path("profileIdentifier") profileIdentifier: String): Response<GravatarProfile>
}