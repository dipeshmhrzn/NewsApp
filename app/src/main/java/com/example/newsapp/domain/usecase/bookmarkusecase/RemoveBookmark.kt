package com.example.newsapp.domain.usecase.bookmarkusecase

import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.repository.BookmarkRepository
import javax.inject.Inject

class RemoveBookmark @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(article: Article) = repository.removeBookmark(article)
}