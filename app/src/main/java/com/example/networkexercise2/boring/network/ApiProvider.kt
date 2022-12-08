package com.example.networkexercise2.boring.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.boredapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    suspend fun getRepos() = apiService.listRepos()
}