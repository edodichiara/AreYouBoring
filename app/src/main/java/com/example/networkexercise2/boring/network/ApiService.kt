package com.example.networkexercise2.boring.network

import com.example.networkexercise2.boring.network.dto.RepoData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/activity")
    suspend fun listRepos(): Response<RepoData>
}