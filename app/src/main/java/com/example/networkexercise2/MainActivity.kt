package com.example.networkexercise2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.networkexercise2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //view model instance
        viewModel =
            (application as MyApplication).mainViewModelFactory.create(MainViewModel::class.java)
        observeRepos()
        binding.btnSelectActivity.setOnClickListener {
            viewModel.retrieveRepos()
        }
        viewModel.retrieveRepos()

    }

    private fun observeRepos() {

        viewModel.repos.observe(this) {
            showRepos(it)
        }

        viewModel.error.observe(this) {
            Log.e("MainActivity1", "error retrieving repos: $it")
            Snackbar.make(
                findViewById(R.id.main_view),
                "Error retrieving repos",
                Snackbar.LENGTH_INDEFINITE
            ).setAction("Retry") { viewModel.retrieveRepos() }.show()
        }
    }

    private fun showRepos(repos: RepoData) {
        binding.tvActivity.text = repos.activity
        binding.tvPrice.text = getString(R.string.tv_price, repos.price.toString())
        binding.tvPartecipants.text = repos.participants.toString()
    }
}