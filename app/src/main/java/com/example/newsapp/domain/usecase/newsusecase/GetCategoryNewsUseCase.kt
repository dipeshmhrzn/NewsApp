package com.example.newsapp.domain.usecase.newsusecase

import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.util.Result
import javax.inject.Inject

class GetCategoryNewsUseCase @Inject constructor (
    private val repository: NewsRepository
){
    suspend operator fun invoke(category: String): Result<List<Article>> {
        return repository.getCategoryNews(category)
    }
}