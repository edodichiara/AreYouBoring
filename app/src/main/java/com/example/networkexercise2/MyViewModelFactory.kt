package com.example.networkexercise2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.networkexercise2.boring.usecase.BoringAppViewModel
import com.example.networkexercise2.boring.network.ApiProvider

class MyViewModelFactory(private val apiProvider: ApiProvider): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BoringAppViewModel::class.java)){
            return BoringAppViewModel(apiProvider) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}