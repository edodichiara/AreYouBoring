package com.example.networkexercise2.boring.usecase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkexercise2.boring.network.ApiProvider
import com.example.networkexercise2.boring.usecase.model.Repository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

sealed class BoringAppResult {
    data class Success(val repos: Repository) : BoringAppResult()
    data class Error(val error: Exception) : BoringAppResult()
}

class BoringAppViewModel(private val apiProvider: ApiProvider) : ViewModel() {

    val result = MutableSharedFlow<BoringAppResult>()
    fun retrieveRepos() {
        viewModelScope.launch {
            try {
                result.emit(BoringAppResult.Success(apiProvider.getRepos()))

            } catch (e: Exception) {
                result.emit(BoringAppResult.Error(e))
            }
        }
    }
}