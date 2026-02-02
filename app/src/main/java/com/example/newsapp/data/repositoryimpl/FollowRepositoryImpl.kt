package com.example.newsapp.data.repositoryimpl

import com.example.newsapp.data.local.FollowDataStore
import com.example.newsapp.domain.repository.FollowRepository
import kotlinx.coroutines.flow.Flow

class FollowRepositoryImpl(
    private val followDataStore: FollowDataStore
) : FollowRepository {

    override fun getSourceIds(): Flow<Set<String>> {
        return followDataStore.getSourceId()
    }

    override suspend fun setSourceId(sourceId: String) {
        followDataStore.setSourceId(sourceId)
    }

    override suspend fun removeSourceId(sourceId: String) {
        followDataStore.removeSourceId(sourceId)
    }
}