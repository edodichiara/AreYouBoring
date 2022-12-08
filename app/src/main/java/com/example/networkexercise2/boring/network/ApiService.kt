package com.example.networkexercise2.boring.network

import com.example.networkexercise2.boring.network.dto.RepoDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/activity")
    suspend fun listRepos(): Response<RepoDTO>
}