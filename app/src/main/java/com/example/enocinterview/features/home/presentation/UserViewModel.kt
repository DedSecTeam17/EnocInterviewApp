package com.example.enocinterview.features.home.presentation

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enocinterview.core.utils.Resource
import com.example.enocinterview.features.home.data.model.AvatarUpdateResponse
import com.example.enocinterview.features.home.data.model.GravatarProfile
import com.example.enocinterview.features.home.data.model.UserResponse
import com.example.enocinterview.features.home.domain.usecases.GetGravatarProfileUseCase
import com.example.enocinterview.features.home.domain.usecases.GetUserUseCase
import com.example.enocinterview.features.home.domain.usecases.UpdateAvatarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.security.MessageDigest

@HiltViewModel
class UserViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateAvatarUseCase: UpdateAvatarUseCase,
    private val getGravatarProfileUseCase: GetGravatarProfileUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<Resource<UserResponse>>(Resource.Loading)
    val userState: StateFlow<Resource<UserResponse>> = _userState

    private val _avatarState = MutableStateFlow<Resource<AvatarUpdateResponse>>(Resource.Loading)
    val avatarState: StateFlow<Resource<AvatarUpdateResponse>> = _avatarState


    private val _gravatarProfileState = MutableStateFlow<Resource<GravatarProfile>>(Resource.Loading)
    val gravatarProfileState: StateFlow<Resource<GravatarProfile>> = _gravatarProfileState

    fun fetchUserData(userId: String) {
        viewModelScope.launch {
            getUserUseCase(userId).collect {
                _userState.value = it
            }
        }
    }

    fun fetchGravatarProfile(email: String) {
        viewModelScope.launch {
            getGravatarProfileUseCase(profileIdentifier = getSha256Hash(email)).collect { resource ->
                _gravatarProfileState.value = resource
            }
        }
    }


    fun updateUserAvatar(context: Context, userId: String, base64String: String?) {
        viewModelScope.launch {
            // Convert Uri to ByteArray
            base64String?.let {
                updateAvatarUseCase(userId, it).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            // Show loading
                        }

                        is Resource.Success -> {
                            fetchUserData(userId = userId)
                            // Handle success (e.g., update UI with new avatar URL)
                        }

                        is Resource.Error -> {
                            // Handle error
                        }

                        Resource.Idle -> {

                        }
                    }
                }
            }
        }
    }

     fun getSha256Hash(email: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(email.trim().toLowerCase().toByteArray())
        return hash.joinToString("") { "%02x".format(it) }
    }
}


