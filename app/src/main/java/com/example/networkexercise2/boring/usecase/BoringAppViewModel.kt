package com.example.networkexercise2.boring.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.networkexercise2.boring.network.ApiProvider
import com.example.networkexercise2.boring.ui.WelcomeDialog
import com.example.networkexercise2.boring.usecase.model.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

sealed class BoringAppResult {
    data class Success(val repos: Repository) : BoringAppResult()
    data class Error(val error: Exception) : BoringAppResult()
}

class BoringAppViewModel(private val apiProvider: ApiProvider) : ViewModel() {


    var dialog = WelcomeDialog()


    private var _result = MutableLiveData<BoringAppResult>()
    val result: LiveData<BoringAppResult>
        get() = _result


    fun retrieveRepos() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                _result.value = BoringAppResult.Success(apiProvider.getRepos())

            } catch (e: Exception) {
                _result.value = BoringAppResult.Error(e)
            }
        }
    }
}