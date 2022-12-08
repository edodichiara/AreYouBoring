package com.example.networkexercise2

import android.app.Application
import android.util.Log
import com.example.networkexercise2.boring.ApiProvider

class MyApplication: Application() {

    private val apiProvider = ApiProvider()
    val mainViewModelFactory = MainViewModelFactory(apiProvider.provide())

    override fun onCreate() {
        super.onCreate()
        Log.d("MyApplication", "Started")
    }
}