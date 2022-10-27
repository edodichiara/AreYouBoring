package com.example.networkexercise2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.boredapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    private var _repos = MutableLiveData<RepoData>()
    val repos: LiveData<RepoData>
        get() = _repos

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun retrieveRepos() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _repos.value = apiService.listRepos().body()

            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }


    }
}