package com.example.networkexercise2.boring.network.dto

import com.example.networkexercise2.boring.usecase.model.Repository


data class RepoDTO(
    val accessibility: Double,
    val activity: String,
    val key: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String
)

fun RepoDTO.toRepository() = Repository(this.activity, this.price, this.participants)