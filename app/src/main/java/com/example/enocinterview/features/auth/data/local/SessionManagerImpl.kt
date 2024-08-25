package com.example.enocinterview.features.auth.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SessionManager {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    companion object {
        private const val USER_ID = "user_id"
        private const val TOKEN = "token"
        private const val FIRST_TIME = "first_time"
        private const val PASSWORD = "password"
        private const val EMAIL = "email"

    }

    override fun saveSession(userId: String, token: String, password: String, email: String) {
        prefs.edit().apply {
            putString(USER_ID, userId)
            putString(TOKEN, token)
            putString(PASSWORD, password)
            putString(EMAIL, email)
            apply()
        }
    }

    override fun getToken(): String? {
        return prefs.getString(TOKEN, null)
    }

    override fun getUserId(): String? {
        return prefs.getString(USER_ID, null)
    }


    override fun getPassword(): String? {
        return prefs.getString(PASSWORD, null)
    }

    override fun clearSession() {
        prefs.edit().putString(TOKEN, "").apply()
        prefs.edit().putString(USER_ID, "").apply()
        prefs.edit().putString(PASSWORD, "").apply()
        prefs.edit().putString(EMAIL, "").apply()

    }

    override fun isFirstTime(): Boolean {
        return prefs.getBoolean(FIRST_TIME, true)
    }

    override fun setFirstTime(isFirstTime: Boolean) {
        prefs.edit().putBoolean(FIRST_TIME, isFirstTime).apply()
    }

    override fun getEmail(): String? {
        return prefs.getString(EMAIL, null)
    }
}