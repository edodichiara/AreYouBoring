package com.example.networkexercise2

import android.app.Application
import android.util.Log
import com.example.networkexercise2.boring.network.ApiProvider

class MyApplication: Application() {

    private val apiProvider = ApiProvider()
    val myViewModelFactory = MyViewModelFactory(apiProvider)

    override fun onCreate() {
        super.onCreate()
        Log.d("MyApplication", "Started")
    }
}