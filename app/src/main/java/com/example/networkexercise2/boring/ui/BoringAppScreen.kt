package com.example.networkexercise2.boring.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.networkexercise2.MyApplication
import com.example.networkexercise2.R
import com.example.networkexercise2.boring.usecase.BoringAppResult
import com.example.networkexercise2.boring.usecase.BoringAppViewModel
import com.example.networkexercise2.boring.usecase.model.Repository
import com.example.networkexercise2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class BoringAppScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: BoringAppViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences("ChangeTheme", Context.MODE_PRIVATE)

        //view model instance
        viewModel =
            (application as MyApplication).myViewModelFactory.create(BoringAppViewModel::class.java)
        observeRepos()
        binding.btnSelectActivity.setOnClickListener {
            viewModel.retrieveRepos()
        }
        viewModel.retrieveRepos()

        val nightMode: Boolean = sharedPreferences.getBoolean("night", false)
        if (nightMode) {
            binding.switchTheme.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding.switchTheme.setOnClickListener {
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences.edit()
                editor.putBoolean("night", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences.edit()
                editor.putBoolean("night", true)
            }
            editor.apply()
        }
    }

    private fun observeRepos() {
        lifecycleScope.launch {
            viewModel.result.collect {
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

    }

    private fun showRepos(repos: Repository) {
        binding.tvActivity.text = repos.activity
        binding.tvPrice.text = getString(R.string.tv_price, repos.price.toString())
        binding.tvPartecipants.text = repos.participants.toString()
    }
}