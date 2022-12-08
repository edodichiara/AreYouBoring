package com.example.networkexercise2.boring.network

import com.example.networkexercise2.boring.network.dto.toRepository
import com.example.networkexercise2.boring.usecase.model.Repository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.boredapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    suspend fun getRepos(): Repository = apiService.listRepos().body()?.toRepository() ?: Repository("error", 0.0, 0)
}