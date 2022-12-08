package com.example.networkexercise2.boring.usecase.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.networkexercise2.boring.network.ApiProvider
import com.example.networkexercise2.boring.network.dto.RepoDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoringAppViewModel(private val apiProvider: ApiProvider) : ViewModel() {

    private var _repos = MutableLiveData<RepoDTO>()
    val repos: LiveData<RepoDTO>
        get() = _repos

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun retrieveRepos() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _repos.value = apiProvider.getRepos().body()

            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }
}