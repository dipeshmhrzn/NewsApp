package com.example.newsapp.domain.usecase.followusecase

import com.example.newsapp.domain.repository.FollowRepository
import javax.inject.Inject

class RemoveSourceIdUseCase @Inject constructor(
    private val repository: FollowRepository
) {
    suspend operator fun invoke(sourceId: String) {
        repository.removeSourceId(sourceId)
    }
}