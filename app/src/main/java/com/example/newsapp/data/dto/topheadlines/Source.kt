package com.example.newsapp.data.dto.topheadlines

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?,
    val name: String
)