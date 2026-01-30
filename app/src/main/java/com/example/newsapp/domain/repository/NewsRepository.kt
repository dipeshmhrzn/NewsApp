package com.example.newsapp.domain.repository

import com.example.newsapp.data.dto.sources.Source
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.util.Result

interface NewsRepository {

    suspend fun getTopHeadlines(): Result<List<Article>>

    suspend fun getCategoryNews(category: String): Result<List<Article>>

    suspend fun getSources(category: String): Result<List<Source>>

    suspend fun getNewsBySources(sourceId: String): Result<List<Article>>

    suspend fun searchNews(query: String): Result<List<Article>>

}
