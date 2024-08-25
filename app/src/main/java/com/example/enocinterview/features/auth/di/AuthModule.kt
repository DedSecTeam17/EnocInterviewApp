package com.example.enocinterview.features.auth.di

import com.example.enocinterview.features.auth.data.api.AuthApiService
import com.example.enocinterview.features.auth.data.api.MockAuthApiService
import com.example.enocinterview.features.auth.data.api.delegate
import com.example.enocinterview.features.auth.data.local.SessionManager
import com.example.enocinterview.features.auth.data.local.SessionManagerImpl
import com.example.enocinterview.features.auth.data.repository.AuthRepositoryImpl
import com.example.enocinterview.features.auth.domain.repository.AuthRepository
import com.example.enocinterview.features.auth.domain.usecases.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApiService(): AuthApiService {
        return MockAuthApiService(delegate)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApiService: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(authApiService)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSessionManager(sessionManagerImpl: SessionManagerImpl): SessionManager {
        return sessionManagerImpl
    }

}