package com.example.homework23

class Repository(client: ApiClient) {
    private val client = client.getService().create(ApiInterface::class.java)
    suspend fun getCurrencyByName(name:String): BitcoinResponse {
        return client.getCryptoByName(name)
    }
}