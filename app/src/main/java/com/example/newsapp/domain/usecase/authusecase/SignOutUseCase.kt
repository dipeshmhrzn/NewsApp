package com.example.newsapp.domain.usecase.authusecase

import com.example.newsapp.domain.repository.AuthRepository
import com.example.newsapp.domain.util.Result
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    val repository: AuthRepository
) {

    suspend operator fun invoke(): Result<String>{
        return repository.signOut()
    }
}