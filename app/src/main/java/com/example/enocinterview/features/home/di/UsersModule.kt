package com.example.enocinterview.features.home.di

import com.example.enocinterview.features.home.data.api.GravatarApiService
import com.example.enocinterview.features.home.data.api.MockUserService
import com.example.enocinterview.features.home.data.api.UserService
import com.example.enocinterview.features.home.data.api.delegate
import com.example.enocinterview.features.home.data.repository.GravatarRepositoryImpl
import com.example.enocinterview.features.home.data.repository.UserRepositoryImpl
import com.example.enocinterview.features.home.domain.repository.GravatarRepository
import com.example.enocinterview.features.home.domain.repository.UserRepository
import com.example.enocinterview.features.home.domain.usecases.GetGravatarProfileUseCase
import com.example.enocinterview.features.home.domain.usecases.GetUserUseCase
import com.example.enocinterview.features.home.domain.usecases.UpdateAvatarUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsersModule {

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
    fun provideGravatarApiService(): GravatarApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.gravatar.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GravatarApiService::class.java)
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

