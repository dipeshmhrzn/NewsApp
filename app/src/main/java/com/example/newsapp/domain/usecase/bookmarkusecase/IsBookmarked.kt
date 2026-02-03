package com.example.newsapp.domain.usecase.bookmarkusecase

import com.example.newsapp.domain.repository.BookmarkRepository
import javax.inject.Inject

class IsBookmarked @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(url: String): Boolean = repository.isBookmarked(url)
}
