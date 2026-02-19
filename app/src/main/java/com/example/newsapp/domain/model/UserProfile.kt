package com.example.newsapp.domain.model

data class UserProfile(
    val userId: String? = null,
    val emailAddress: String = "",
    val profilePicture: String? = null,
)