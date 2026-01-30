package com.example.newsapp.data.dto.sources

import kotlinx.serialization.Serializable

@Serializable
data class SourcesDto(
    val sources: List<Source>,
    val status: String
)