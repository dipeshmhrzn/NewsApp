package com.example.newsapp.domain.usecase

import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.util.Result
import javax.inject.Inject

class GetNewsBySourcesUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(sourceId: String): Result<List<Article>> {
        return repository.getNewsBySources(sourceId)
    }

}