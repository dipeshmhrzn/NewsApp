package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.UserProfile
import com.example.newsapp.domain.util.Result

interface UserProfileRepository {

    suspend fun saveUserProfile(userProfile: UserProfile): Result<String>

    suspend fun getUserProfile(userId: String): Result<UserProfile>

}