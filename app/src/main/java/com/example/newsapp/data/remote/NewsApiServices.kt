package com.example.newsapp.data.remote

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.dto.sources.SourcesDto
import com.example.newsapp.data.dto.topheadlines.NewsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NewsApiServices(
    val httpClient: HttpClient
) {
    suspend fun getTopHeadlines(
        country: String? = "us",
        category: String? = null,
        sourceId: String? = null
    ): NewsDto {
        return httpClient.get("top-headlines") {
            parameter("country", country)
            parameter("category", category)
            parameter("sources", sourceId)
            parameter("apiKey", BuildConfig.API_KEY)
        }.body<NewsDto>()
    }

    suspend fun getSources(category: String): SourcesDto {
        return httpClient.get("top-headlines/sources") {
            parameter("category", category)
            parameter("apiKey", BuildConfig.API_KEY)
        }.body<SourcesDto>()
    }

    suspend fun searchNews(query: String): NewsDto {
        return httpClient.get("everything") {
            parameter("q", query)
            parameter("apiKey", BuildConfig.API_KEY)
        }.body<NewsDto>()
    }

}