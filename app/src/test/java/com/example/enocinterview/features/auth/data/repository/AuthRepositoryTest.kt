package com.example.enocinterview.features.auth.data.repository

import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.auth.data.api.AuthApiService
import com.example.enocinterview.features.auth.data.api.MockAuthApiService
import com.example.enocinterview.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

@ExperimentalCoroutinesApi
class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var mockAuthApiService: AuthApiService
    private lateinit var behavior: NetworkBehavior
    private lateinit var delegate: BehaviorDelegate<AuthApiService>

    @Before
    fun setUp() {
        // Initialize Retrofit and MockRetrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mockapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        behavior = NetworkBehavior.create()
        val mockRetrofit = MockRetrofit.Builder(retrofit)
            .networkBehavior(behavior)
            .build()

        delegate = mockRetrofit.create(AuthApiService::class.java)
        mockAuthApiService = MockAuthApiService(delegate)

        authRepository = AuthRepositoryImpl(mockAuthApiService)
    }

    @Test
    fun `login success returns userid and token`() = runTest {
        // Simulate a successful login response
        behavior.setErrorPercent(0) // No errors
        val results = authRepository.login("test@example.com", "password").toList()
        assertTrue(results[0] is Resource.Loading)

        assertTrue(results[1] is Resource.Success)

        val successResult = results[1]

        assertEquals("283746", (successResult as Resource.Success).data.userid)
    }

    @Test
    fun `login failure returns error`() = runTest {
        // Simulate a failed login response
        behavior.setErrorPercent(100) // Force error
        val results = authRepository.login("test@example.com", "wrong_password").toList()

        val errorResult = results[1]

        assertTrue(errorResult is Resource.Error)

        assertEquals("Login failed", (errorResult as Resource.Error).message)
    }
}