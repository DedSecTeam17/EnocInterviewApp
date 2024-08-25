package com.example.enocinterview

import com.example.enocinterview.features.home.data.api.GravatarApiService
import com.example.enocinterview.features.home.data.api.MockUserService
import com.example.enocinterview.features.home.data.api.UserService
import com.example.enocinterview.features.home.data.api.delegate
import com.example.enocinterview.features.home.data.model.GravatarProfile
import com.example.enocinterview.features.home.data.repository.GravatarRepositoryImpl
import com.example.enocinterview.features.home.data.repository.UserRepositoryImpl
import com.example.enocinterview.features.home.di.UsersModule
import com.example.enocinterview.features.home.domain.repository.GravatarRepository
import com.example.enocinterview.features.home.domain.repository.UserRepository
import com.example.enocinterview.features.home.domain.usecases.GetGravatarProfileUseCase
import com.example.enocinterview.features.home.domain.usecases.GetUserUseCase
import com.example.enocinterview.features.home.domain.usecases.UpdateAvatarUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UsersModule::class] // Replace the original module with this one in the test environment
)
object TestUsersModule {

    @Provides
    @Singleton
    fun provideGravatarApiService(): GravatarApiService {
        return MockGravatarApiService()
    }

    @Provides
    @Singleton
    fun provideUserService(): UserService {
        return MockUserService(delegate)
    }

    @Provides
    @Singleton
    fun provideUserRepository(api: UserService): UserRepository {
        return UserRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(userRepository: UserRepository): GetUserUseCase {
        return GetUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateAvatarUseCase(userRepository: UserRepository): UpdateAvatarUseCase {
        return UpdateAvatarUseCase(userRepository)
    }



    @Provides
    @Singleton
    fun provideGravatarRepository(apiService: GravatarApiService): GravatarRepository {
        return GravatarRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideGetGravatarProfileUseCase(repository: GravatarRepository): GetGravatarProfileUseCase {
        return GetGravatarProfileUseCase(repository)
    }
}

class MockGravatarApiService : GravatarApiService {

    override suspend fun getProfile(profileIdentifier: String): Response<GravatarProfile> {
        return Response.success(
            GravatarProfile(
                hash = "31c5543c1734d25c7206f5fd591525d0295bec6fe84ff82f946a34fe970a1e66",
                display_name = "Alex Morgan",
                profile_url = "https://gravatar.com/example",
                avatar_url = "https://0.gravatar.com/avatar/33252cd1f33526af53580fcb1736172f06e6716f32afdd1be19ec3096d15dea5",
                avatar_alt_text = "Alex Morgan's avatar image. Alex is smiling and standing in beside a large dog who is looking up at Alex.",
                location = "New York, USA",
                description = "I like playing hide and seek.",
                job_title = "Landscape Architect",
                company = "ACME Corp",
                verified_accounts = emptyList(),
                pronunciation = "Al-ex Mor-gan",
                pronouns = "She/They",
                timezone = "Europe/Bratislava",
                languages = emptyList(),
                first_name = "Alex",
                last_name = "Morgan",
                is_organization = false,
                links = emptyList(),
                interests = emptyList(),
                payments = null,
                contact_info = null,
                gallery = emptyList(),
                number_verified_accounts = 3,
                last_profile_edit = "2021-10-01T12:00:00Z",
                registration_date = "2021-10-01T12:00:00Z",
                error = null
            )
        )
    }

    // Implement other methods if needed, returning mock data
}