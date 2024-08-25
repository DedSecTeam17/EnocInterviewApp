package com.example.enocinterview.core.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import okhttp3.OkHttpClient

fun createMockRetrofit(): MockRetrofit {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.yourapp.com/") // Base URL is required but won't be used
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    val behavior = NetworkBehavior.create()

    return MockRetrofit.Builder(retrofit)
        .networkBehavior(behavior)
        .build()
}