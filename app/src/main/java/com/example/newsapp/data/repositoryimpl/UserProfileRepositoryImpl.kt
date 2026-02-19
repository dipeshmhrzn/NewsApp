package com.example.newsapp.data.repositoryimpl

import com.example.newsapp.domain.model.UserProfile
import com.example.newsapp.domain.repository.UserProfileRepository
import com.example.newsapp.domain.util.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserProfileRepositoryImpl(
    private val firebaseFireStore: FirebaseFirestore,
) : UserProfileRepository {

    override suspend fun saveUserProfile(userProfile: UserProfile): Result<String> {
        return try {
            val userId = userProfile.userId ?: return Result.Error("User id not found !")
            firebaseFireStore.collection("users").document(userId).set(userProfile).await()
            Result.Success("Profile saved !")
        } catch (e: Exception) {
            Result.Error("Error saving user profile: ${e.message}")
        }
    }

    override suspend fun getUserProfile(userId: String): Result<UserProfile> {
        return try {
            val userRef = firebaseFireStore.collection("users").document(userId)
            val document = userRef.get().await()
            if (document.exists()) {
                val userProfile = document.toObject(UserProfile::class.java) ?: UserProfile()
                Result.Success(userProfile)
            }else {
                Result.Error("User profile not found")
            }
        } catch (e: Exception) {
            Result.Error("Error fetching user profile: ${e.message}")
        }
    }


}