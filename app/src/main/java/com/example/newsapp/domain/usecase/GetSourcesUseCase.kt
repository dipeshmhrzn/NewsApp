package com.example.newsapp.domain.usecase

import com.example.newsapp.data.dto.sources.Source
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.util.Result
import javax.inject.Inject

class GetSourcesUseCase @Inject constructor(
    private val repository: NewsRepository
){
    suspend operator fun invoke(category: String) : Result<List<Source>> {
        return repository.getSources(category)
    }
}