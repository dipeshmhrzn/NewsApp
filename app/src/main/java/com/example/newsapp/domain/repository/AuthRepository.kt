package com.example.newsapp.domain.repository

import com.example.newsapp.domain.util.Result

interface AuthRepository{

    suspend fun login(email:String, password:String): Result<String>

    suspend fun signup(email: String,password: String):Result<String>

    suspend fun signOut():Result<String>
}