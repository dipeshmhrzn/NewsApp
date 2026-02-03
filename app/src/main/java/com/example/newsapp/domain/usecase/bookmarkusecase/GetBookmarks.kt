package com.example.newsapp.domain.usecase.bookmarkusecase

import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarks @Inject constructor(
    private val repository: BookmarkRepository
) {
    operator fun invoke(): Flow<List<Article>> = repository.getBookmarks()
}
