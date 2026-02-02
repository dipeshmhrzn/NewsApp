package com.example.newsapp.domain.usecase.followusecase

import com.example.newsapp.domain.repository.FollowRepository
import javax.inject.Inject

class SetSourceIdUseCase @Inject constructor(
    private val repository: FollowRepository
) {
    suspend operator fun invoke(sourceId: String) {
        repository.setSourceId(sourceId)
    }
}