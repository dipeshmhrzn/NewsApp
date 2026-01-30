package com.example.newsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.dto.sources.Source
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.usecase.GetCategoryNewsUseCase
import com.example.newsapp.domain.usecase.GetNewsBySourcesUseCase
import com.example.newsapp.domain.usecase.GetSourcesUseCase
import com.example.newsapp.domain.usecase.GetTopHeadlineUseCase
import com.example.newsapp.domain.usecase.SearchNewsUseCase
import com.example.newsapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlineUseCase,
    private val getCategoryNewsUseCase: GetCategoryNewsUseCase,
    private val getSourcesUseCase: GetSourcesUseCase,
    private val getNewsBySourcesUseCase: GetNewsBySourcesUseCase,
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {

    private val _newsState = MutableStateFlow<Result<List<Article>>>(Result.Idle)
    val newsState = _newsState.asStateFlow()

    private val _categoryNewsState = MutableStateFlow<Result<List<Article>>>(Result.Idle)
    val categoryNewsState = _categoryNewsState.asStateFlow()

    private val _searchState = MutableStateFlow<Result<List<Article>>>(Result.Idle)
    val searchState = _searchState.asStateFlow()

    private val _sourcesByCategory = MutableStateFlow<Map<String, Result<List<Source>>>>(emptyMap())
    val sourcesByCategory = _sourcesByCategory.asStateFlow()

    private val _newsBySources = MutableStateFlow<Result<List<Article>>>(Result.Idle)
    val newsBySources = _newsBySources.asStateFlow()

    private var cachedArticles: List<Article>? = null

    private var searchJob: Job? = null


    init {
        getTopHeadlines()
        getCategoryNews("business")
    }

    fun getTopHeadlines() {

        if (!cachedArticles.isNullOrEmpty()) {
            _newsState.value = Result.Success(cachedArticles!!)
            return
        }

        viewModelScope.launch {
            _newsState.value = Result.Loading
            val topHeadlines = getTopHeadlinesUseCase()
            when (topHeadlines) {
                is Result.Success -> {
                    _newsState.value = Result.Success(topHeadlines.data)
                }

                else -> {

                }
            }
        }
    }

    fun getNewsBySources(sourceId:String){
        viewModelScope.launch {
            _newsBySources.value = Result.Loading
            val newsBySources = getNewsBySourcesUseCase(sourceId)

            when (newsBySources) {
                is Result.Success -> {
                    _newsBySources.value = Result.Success(newsBySources.data)
                }

                else -> {

                }
            }
        }
    }

    fun getCategoryNews(category: String) {
        viewModelScope.launch {
            _categoryNewsState.value = Result.Loading
            val categoryNews = getCategoryNewsUseCase(category)
            when (categoryNews) {
                is Result.Success -> {
                    _categoryNewsState.value = Result.Success(categoryNews.data)
                }

                else -> {

                }
            }
        }
    }

    fun getSources(category: String) {
        viewModelScope.launch {
            _sourcesByCategory.value = _sourcesByCategory.value + (category to Result.Loading)

            val sources = getSourcesUseCase(category.lowercase())
            when (sources) {
                is Result.Success -> {
                    _sourcesByCategory.value = _sourcesByCategory.value + (category to sources)
                }

                else -> {

                }
            }
        }
    }

    fun searchProducts(query: String) {
        // Cancel previous search job
        searchJob?.cancel()

        if (query.isBlank()) {
            _searchState.value = Result.Idle
            return
        }

        // Debounce search - wait 300ms before executing
        searchJob = viewModelScope.launch {
            delay(300)
            _searchState.value = Result.Loading
            try {
                val result = searchNewsUseCase(query)
                _searchState.value = result
            } catch (e: Exception) {
                _searchState.value = Result.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun clearSearch() {
        searchJob?.cancel()
        _searchState.value = Result.Idle
    }


}