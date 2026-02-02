package com.example.newsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecase.followusecase.GetSourceIdUseCase
import com.example.newsapp.domain.usecase.followusecase.RemoveSourceIdUseCase
import com.example.newsapp.domain.usecase.followusecase.SetSourceIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    val getSourceIdUseCase: GetSourceIdUseCase,
    val setSourceIdUseCase: SetSourceIdUseCase,
    val removeSourceIdUseCase: RemoveSourceIdUseCase
) : ViewModel() {

    val followedSourceIds = getSourceIdUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptySet()
        )

    init {
        viewModelScope.launch {
            followedSourceIds.collect { sourceIds ->
                Log.d("FollowViewModel", "Followed Source IDs: $sourceIds")
            }
        }
    }


    fun followSource(sourceId: String) {
        viewModelScope.launch {
            setSourceIdUseCase(sourceId)
        }
    }

    fun unfollowSource(sourceId: String) {
        viewModelScope.launch {
            removeSourceIdUseCase(sourceId)
        }
    }
}