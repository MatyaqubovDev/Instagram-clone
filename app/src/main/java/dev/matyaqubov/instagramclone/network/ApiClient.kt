package dev.matyaqubov.instagramclone.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val IS_TESTER = false
    private val SERVER_DEVELOPMENT = "https://6260f1b3f429c20deb981149.mockapi.io/"
    private val SERVER_PRODUCTION = "https://fcm.googleapis.com/fcm/"

    val retrofit =
        Retrofit.Builder().baseUrl(server()).addConverterFactory(GsonConverterFactory.create())
            .build()

    private fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}