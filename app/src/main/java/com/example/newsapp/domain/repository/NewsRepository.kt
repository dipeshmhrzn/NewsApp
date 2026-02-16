package com.example.newsapp.domain.repository

import com.example.newsapp.data.dto.sources.Source
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.util.Result

interface NewsRepository {

    suspend fun getTopHeadlines(page: Int = 1, pageSize: Int = 20): Result<List<Article>>

    suspend fun getCategoryNews(category: String, page: Int = 1, pageSize: Int = 20): Result<List<Article>>

    suspend fun getSources(category: String): Result<List<Source>>

    suspend fun getNewsBySources(sourceId: String, page: Int = 1, pageSize: Int = 20): Result<List<Article>>

    suspend fun searchNews(query: String): Result<List<Article>>

}
