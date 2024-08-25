package com.example.enocinterview.features.auth.data.model


data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val userid: String, val token: String)