package com.example.newsapp.domain.usecase.newsusecase

import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.util.Result
import javax.inject.Inject

class GetTopHeadlineUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(page:Int=1, pageSize:Int=20): Result<List<Article>> {
        return repository.getTopHeadlines(page,pageSize)
    }
}