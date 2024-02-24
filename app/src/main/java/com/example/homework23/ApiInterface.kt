package com.example.homework23

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("/v2/rates/{cryptoName}")
    suspend fun getCryptoByName(@Path("cryptoName") name: String): BitcoinResponse
}