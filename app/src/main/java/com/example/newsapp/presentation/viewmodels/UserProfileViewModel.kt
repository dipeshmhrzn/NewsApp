package com.example.newsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.UserProfile
import com.example.newsapp.domain.usecase.authusecase.GetCurrentUserIdUseCase
import com.example.newsapp.domain.usecase.profileusecase.ProfileUseCase
import com.example.newsapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userProfileUseCase: ProfileUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
) : ViewModel() {

    private val _userProfile = MutableStateFlow<Result<UserProfile?>>(Result.Idle)
    val userProfile = _userProfile.asStateFlow()
    fun getUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _userProfile.value = Result.Loading
            val userId = getCurrentUserIdUseCase()
            Log.d("UserProfileViewModel", "Fetching profile for userId: $userId")
            if (userId == null) {
                _userProfile.value = Result.Error("User not logged in")
                return@launch
            }

            val result = userProfileUseCase.getUserProfile(userId)
            _userProfile.value = result
        }
    }

    fun clearUserProfile() {
        _userProfile.value = Result.Idle
    }

}