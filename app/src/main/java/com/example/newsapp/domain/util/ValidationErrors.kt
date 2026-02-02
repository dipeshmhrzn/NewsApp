package com.example.newsapp.domain.util

sealed class ValidationErrors {

    data class EmailError(val message: String): ValidationErrors()

    data class PasswordError(val message: String):ValidationErrors()
}