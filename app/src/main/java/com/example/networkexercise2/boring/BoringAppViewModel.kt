package com.example.networkexercise2.boring

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoringAppViewModel(private val apiService: ApiService) : ViewModel() {

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