package com.example.newsapp.domain.usecase.authusecase

import com.example.newsapp.domain.repository.AuthRepository
import com.example.newsapp.domain.util.Result
import com.example.newsapp.domain.util.ValidationErrors
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String): Result<String>{

        if (email.isBlank()){
            return Result.Error(ValidationErrors.EmailError("Email cannot be empty"))
        }

        return authRepository.resetPassword(email)
    }
}