package com.example.newsapp.data.repositoryimpl

import com.example.newsapp.domain.repository.AuthRepository
import com.example.newsapp.domain.util.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<String> {

        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.Success("Login Successful!")
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Error occurred !!")
        }

    }

    override suspend fun signup(
        email: String,
        password: String
    ): Result<String> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.Success("Signup Successful!")
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Error occurred !!")
        }
    }

    override suspend fun signOut(): Result<String> {
        return try {
            firebaseAuth.signOut()
            Result.Success("Logout Successful!")
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "Error occurred !!")
        }
    }


}