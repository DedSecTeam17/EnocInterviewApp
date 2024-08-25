package com.example.enocinterview.features.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.auth.data.local.SessionManager
import com.example.enocinterview.features.auth.data.model.LoginResponse
import com.example.enocinterview.features.auth.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    val sessionManager: SessionManager // Inject SessionManager interface
) : ViewModel() {



     var _loginState = MutableStateFlow<Resource<LoginResponse>>(Resource.Idle)
    open  var loginState: StateFlow<Resource<LoginResponse>> = _loginState

    open fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).collect {
                if (it is Resource.Success) {
                    // Save session
                    sessionManager.saveSession(it.data.userid, it.data.token,password,email)
                }
                _loginState.value = it
            }
        }
    }
}

