package com.example.newsapp.domain.model

data class AuthDataStoreState(
    val isFirstTimeLogin: Boolean = true,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = true
)