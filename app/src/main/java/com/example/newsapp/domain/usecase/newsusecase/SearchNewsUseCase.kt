package com.example.newsapp.domain.usecase.newsusecase

import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.util.Result
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(query: String): Result<List<Article>> {
        return if (query.isBlank()) {
            Result.Success(emptyList())
        } else {
            newsRepository.searchNews(query)
        }
    }
}