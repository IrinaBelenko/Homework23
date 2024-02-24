package com.example.homework23

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    fun getService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.coincap.io/")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}