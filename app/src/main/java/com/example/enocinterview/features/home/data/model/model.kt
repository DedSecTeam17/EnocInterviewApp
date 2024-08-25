package com.example.enocinterview.features.home.data.model

data class UserResponse(
    val email: String,
    val avatar_url: String
)

data class AvatarUpdateRequest(
    val avatar: String
)

data class AvatarUpdateResponse(
    val avatar_url: String
)