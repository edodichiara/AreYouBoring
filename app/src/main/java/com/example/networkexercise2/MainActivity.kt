package com.example.networkexercise2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.networkexercise2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiService {
    @GET("api/activity")
    suspend fun listRepos(): Response<RepoData>
}

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.boredapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnSelectActivity.setOnClickListener {
            retrieveRepos()
        }
        retrieveRepos()

    }

    fun retrieveRepos() {
        lifecycleScope.launch {
            try {
                val repos = apiService.listRepos()
                showRepos(repos)
                Log.v("MainActivity1", "Body: ${repos}")
            } catch (e: Exception) {
                Log.e("MainActivity1", "error retrieving repos: $e")
                Snackbar.make(
                    findViewById(R.id.main_view),
                    "Error retrieving repos",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Retry") { retrieveRepos() }.show()
            }

        }
    }

    fun showRepos(repos: Response<RepoData>) {
        binding.tvActivity.text = repos.body()!!.activity
        binding.tvPrice.text = getString(R.string.tv_price, repos.body()!!.price.toString())
        binding.tvPartecipants.text = repos.body()!!.participants.toString()
    }
}