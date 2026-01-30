package com.example.newsapp.data.repositoryimpl

import android.util.Log
import com.example.newsapp.data.dto.sources.Source
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.data.remote.NewsApiServices
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.util.Result

class NewsRepositoryImpl(
    private val newsApiServices: NewsApiServices
) : NewsRepository {

    override suspend fun getTopHeadlines(): Result<List<Article>> {
        return try {
            val topHeadlinesResponse = newsApiServices.getTopHeadlines()
            Log.d("NewsRepositoryImpl", "getTopHeadlines: ${topHeadlinesResponse.articles}")
            Result.Success(topHeadlinesResponse.articles)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun getCategoryNews(category: String): Result<List<Article>> {
        return try {
            val categoryNewsResponse = newsApiServices.getTopHeadlines(category = category)
            Log.d(
                "NewsRepositoryImpl",
                "CategoryNews: ${categoryNewsResponse.articles.size},${categoryNewsResponse.totalResults}"
            )
            Result.Success(categoryNewsResponse.articles)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun getSources(category: String): Result<List<Source>> {
        return try {
            val sourcesResponse = newsApiServices.getSources(category)
            Log.d("NewsRepositoryImpl", "Sources: ${sourcesResponse.sources}")
            Result.Success(sourcesResponse.sources)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun getNewsBySources(sourceId: String): Result<List<Article>> {
        return try {
            val sourceNewsResponse = newsApiServices.getTopHeadlines(country = null, sourceId = sourceId)
            Log.d("SourcesNews", "getTopHeadlines: ${sourceNewsResponse.articles}")
            Result.Success(sourceNewsResponse.articles)
        } catch (e: Exception) {
            Log.d("SourcesNews", "getTopHeadlines: ${e.localizedMessage}")
            Result.Error(e.localizedMessage ?: "An error occurred")
        }
    }

    override suspend fun searchNews(query: String): Result<List<Article>> {
        return try {
            val searchNewsResponse = newsApiServices.searchNews(query)
            Log.d("NewsRepositoryImpl", "searchNews: ${searchNewsResponse.articles}")
            Result.Success(searchNewsResponse.articles)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An error occurred")
        }
    }

}