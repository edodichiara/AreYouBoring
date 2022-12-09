package com.example.networkexercise2.boring.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.networkexercise2.MyApplication
import com.example.networkexercise2.R
import com.example.networkexercise2.boring.usecase.BoringAppResult
import com.example.networkexercise2.boring.usecase.BoringAppViewModel
import com.example.networkexercise2.boring.usecase.model.Repository
import com.example.networkexercise2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class BoringAppScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BoringAppViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //view model instance
        viewModel =
            (application as MyApplication).myViewModelFactory.create(BoringAppViewModel::class.java)
        observeRepos()
        binding.btnSelectActivity.setOnClickListener {
            viewModel.retrieveRepos()
        }
        viewModel.retrieveRepos()
        viewModel.dialog.show(supportFragmentManager, "custom")

    }

    private fun observeRepos() {

        viewModel.result.observe(this) {
            when (it) {
                is BoringAppResult.Error -> {
                    Snackbar.make(
                        findViewById(R.id.main_view),
                        "Error retrieving repos",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("Retry") { viewModel.retrieveRepos() }.show()
                }
                is BoringAppResult.Success -> showRepos(it.repos)
            }
        }
    }

    private fun showRepos(repos: Repository) {
        binding.tvActivity.text = repos.activity
        binding.tvPrice.text = getString(R.string.tv_price, repos.price.toString())
        binding.tvPartecipants.text = repos.participants.toString()
    }
}