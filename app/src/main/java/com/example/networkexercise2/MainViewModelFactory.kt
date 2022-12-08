package com.example.networkexercise2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.networkexercise2.boring.ApiService
import com.example.networkexercise2.boring.BoringAppViewModel

class MainViewModelFactory(private val apiService: ApiService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BoringAppViewModel::class.java)){
            return BoringAppViewModel(apiService) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}