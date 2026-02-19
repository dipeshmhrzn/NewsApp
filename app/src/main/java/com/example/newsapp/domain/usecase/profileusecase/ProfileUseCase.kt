package com.example.newsapp.domain.usecase.profileusecase

import com.example.newsapp.domain.model.UserProfile
import com.example.newsapp.domain.repository.UserProfileRepository
import com.example.newsapp.domain.util.Result
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {

    suspend fun saveUserProfile(userProfile: UserProfile): Result<String> {
        return repository.saveUserProfile(userProfile)
    }

    suspend fun getUserProfile(userId: String): Result<UserProfile?> {
        return repository.getUserProfile(userId)
    }

}