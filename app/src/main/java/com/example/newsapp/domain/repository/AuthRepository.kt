package com.example.newsapp.domain.repository

import com.example.newsapp.domain.util.Result
import com.google.firebase.auth.FirebaseUser

interface AuthRepository{

    suspend fun login(email:String, password:String): Result<String>

    suspend fun signInWithGoogle(idToken:String):Result<FirebaseUser>

    suspend fun signup(email: String,password: String):Result<String>

    suspend fun resetPassword(email: String): Result<String>

    suspend fun signOut():Result<String>

    fun getCurrentUserId(): String?

}