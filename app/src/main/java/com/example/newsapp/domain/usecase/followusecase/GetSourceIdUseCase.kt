package com.example.newsapp.domain.usecase.followusecase

import com.example.newsapp.domain.repository.FollowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSourceIdUseCase @Inject constructor(
    private val repository: FollowRepository
) {
    operator fun invoke(): Flow<Set<String>> {
        return repository.getSourceIds()
    }
}