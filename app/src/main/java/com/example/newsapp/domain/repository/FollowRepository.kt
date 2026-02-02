package com.example.newsapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface FollowRepository {

    fun getSourceIds(): Flow<Set<String>>

    suspend fun setSourceId(sourceId: String)

    suspend fun removeSourceId(sourceId: String)

}