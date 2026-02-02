package com.example.newsapp.domain.usecase.authusecase

import android.util.Patterns
import com.example.newsapp.domain.repository.AuthRepository
import com.example.newsapp.domain.util.Result
import com.example.newsapp.domain.util.ValidationErrors
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        if (email.isBlank()) {
            return Result.Error(ValidationErrors.EmailError("Email cannot be empty!"))
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result.Error(ValidationErrors.EmailError("Invalid email format!"))
        }
        if (password.length < 6) {
            return Result.Error(ValidationErrors.PasswordError("Password should be at least 6 characters!"))
        }
        return repository.signup(email,password)
    }
}
