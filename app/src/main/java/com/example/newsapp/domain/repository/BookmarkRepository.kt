package com.example.newsapp.domain.repository

import com.example.newsapp.data.dto.topheadlines.Article
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarks(): Flow<List<Article>>
    suspend fun addBookmark(article: Article)
    suspend fun removeBookmark(article: Article)
    suspend fun isBookmarked(url: String): Boolean
}
