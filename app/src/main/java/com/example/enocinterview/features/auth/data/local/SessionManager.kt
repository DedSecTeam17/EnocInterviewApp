package com.example.enocinterview.features.auth.data.local

interface SessionManager {
    fun saveSession(userId: String, token: String,password: String, email: String)
    fun getToken(): String?
    fun getUserId(): String?
    fun getPassword(): String?
    fun getEmail(): String?
    fun clearSession()
    fun isFirstTime(): Boolean
    fun setFirstTime(isFirstTime: Boolean)
}