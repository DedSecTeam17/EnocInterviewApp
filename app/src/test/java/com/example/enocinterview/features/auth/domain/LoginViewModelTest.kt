package com.example.enocinterview.features.auth.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.auth.data.local.SessionManager
import com.example.enocinterview.features.auth.data.model.LoginResponse
import com.example.enocinterview.features.auth.domain.usecases.LoginUseCase
import com.example.enocinterview.features.auth.presentation.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.anyString
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var loginUseCase: LoginUseCase

    @Mock
    private lateinit var sessionManager: SessionManager

    private lateinit var loginViewModel: LoginViewModel

    // Use TestCoroutineDispatcher for testing
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Set the Main dispatcher to the test dispatcher
        Dispatchers.setMain(testDispatcher)

        loginViewModel = LoginViewModel(loginUseCase, sessionManager)
    }

    @After
    fun tearDown() {
        // Reset the Main dispatcher to the original Main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `successful login saves session`() = runTest(testDispatcher) {
        // Given
        val loginResponse = LoginResponse(userid = "mockUserId", token = "mockToken")
        `when`(loginUseCase("test@example.com", "password")).thenReturn(flowOf(Resource.Success(loginResponse)))

        // When
        loginViewModel.login("test@example.com", "password")

        // Advance time to allow for flow emission
        advanceUntilIdle()

        // Then
        verify(sessionManager).saveSession("mockUserId", "mockToken", password = "password", email = "test@example.com")
    }

    @Test
    fun `failed login does not save session`() = runTest(testDispatcher) {
        // Given
        `when`(loginUseCase("test@example.com", "wrong_password")).thenReturn(flowOf(Resource.Error("Login failed")))

        // When
        loginViewModel.login("test@example.com", "wrong_password")

        // Then
        verify(sessionManager, never()).saveSession(anyString(), anyString(), password = anyString(), email = anyString())
    }

    @Test
    fun `loading state is emitted during login`() = runTest(testDispatcher) {
        // Given
        val loginResponse = LoginResponse(userid = "mockUserId", token = "mockToken")
        `when`(loginUseCase("test@example.com", "password")).thenReturn(flow {
            emit(Resource.Idle)
            emit(Resource.Loading)
            emit(Resource.Success(loginResponse))
        })

        // Collect emitted states
        val stateList = mutableListOf<Resource<LoginResponse>>()
        val job = launch {
            loginViewModel.loginState.collect { state ->
                println(state)
                stateList.add(state)
            }
        }

        // When
        loginViewModel.login("test@example.com", "password")

        // Advance coroutine execution
        runCurrent()

        // Cancel the job after collecting
        job.cancel()


        println(stateList)
        // Then
        assert(stateList[0] is Resource.Idle)
        assert(stateList[1] is Resource.Success)
    }
}