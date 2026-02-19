package com.example.newsapp.domain.usecase.authusecase

import com.example.newsapp.domain.repository.AuthRepository
import com.example.newsapp.domain.util.Result
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor (
    private val repository: AuthRepository
){

    suspend operator fun invoke(idToken:String): Result<FirebaseUser>{
        return repository.signInWithGoogle(idToken)
    }

}