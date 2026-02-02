package com.example.newsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.dto.sources.Source
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.usecase.newsusecase.GetCategoryNewsUseCase
import com.example.newsapp.domain.usecase.newsusecase.GetNewsBySourcesUseCase
import com.example.newsapp.domain.usecase.newsusecase.GetSourcesUseCase
import com.example.newsapp.domain.usecase.newsusecase.GetTopHeadlineUseCase
import com.example.newsapp.domain.usecase.newsusecase.SearchNewsUseCase
import com.example.newsapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.isActive


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

    private var cachedTopHeadlines: List<Article>? = null
    private val cachedCategoryNews = mutableMapOf<String, List<Article>>()
    private val cachedSourcesByCategory = mutableMapOf<String, List<Source>>()
    private val cachedNewsBySources = mutableMapOf<String, List<Article>>()
    private var cachedSearchResults: List<Article>? = null

    private var searchJob: Job? = null


    init {
        getTopHeadlines()
        getCategoryNews("business")
    }

    fun getTopHeadlines() {
        cachedTopHeadlines?.let {
            _newsState.value = Result.Success(it)
            return
        }

        viewModelScope.launch {
            _newsState.value = Result.Loading
            val result = getTopHeadlinesUseCase()
            _newsState.value = when (result) {
                is Result.Success -> {
                    cachedTopHeadlines = result.data
                    Result.Success(result.data)
                }
                is Result.Error -> Result.Error("Error fetching news")
                else -> Result.Idle
            }
        }
    }

    fun getNewsBySources(sourceId: String) {
        cachedNewsBySources[sourceId]?.let {
            _newsBySources.value = Result.Success(it)
            return
        }

        viewModelScope.launch {
            _newsBySources.value = Result.Loading
            val result = getNewsBySourcesUseCase(sourceId)
            _newsBySources.value = when (result) {
                is Result.Success -> {
                    cachedNewsBySources[sourceId] = result.data
                    Result.Success(result.data)
                }
                is Result.Error -> Result.Error("Error fetching news")
                else -> Result.Idle
            }
        }
    }

    fun getCategoryNews(category: String) {
        cachedCategoryNews[category]?.let {
            _categoryNewsState.value = Result.Success(it)
            return
        }

        viewModelScope.launch {
            _categoryNewsState.value = Result.Loading
            val result = getCategoryNewsUseCase(category)
            _categoryNewsState.value = when (result) {
                is Result.Success -> {
                    cachedCategoryNews[category] = result.data
                    Result.Success(result.data)
                }
                is Result.Error -> Result.Error("Error fetching news")
                else -> Result.Idle
            }
        }
    }

    fun getSources(category: String) {
        cachedSourcesByCategory[category]?.let {
            _sourcesByCategory.value = _sourcesByCategory.value + (category to Result.Success(it))
            return
        }

        viewModelScope.launch {
            _sourcesByCategory.value = _sourcesByCategory.value + (category to Result.Loading)
            val result = getSourcesUseCase(category.lowercase())
            _sourcesByCategory.value = when (result) {
                is Result.Success -> {
                    cachedSourcesByCategory[category] = result.data
                    _sourcesByCategory.value + (category to result)
                }
                is Result.Error -> _sourcesByCategory.value + (category to result)
                else -> _sourcesByCategory.value + (category to Result.Idle)
            }
        }
    }

    fun searchNews(query: String) {
        // Cancel any ongoing search job
        searchJob?.cancel()

        // If query is blank, reset state
        if (query.isBlank()) {
            _searchState.value = Result.Idle
            return
        }

        searchJob = viewModelScope.launch {
            try {
                // Debounce: wait a little before sending search request
                delay(400)

                // Check if coroutine was cancelled during delay
                if (!isActive) return@launch

                // Set loading state
                _searchState.value = Result.Loading

                // Call the search use case
                val result = searchNewsUseCase(query)

                // Update state only if coroutine still active
                if (isActive) {
                    _searchState.value = when (result) {
                        is Result.Success -> Result.Success(result.data)
                        is Result.Error -> Result.Error(result.message)
                        else -> Result.Idle
                    }
                }

            } catch (e: Exception) {
                if (e !is CancellationException) {
                    _searchState.value = Result.Error("Failed to search news.")
                }
            }
        }
    }



    fun clearSearch() {
        searchJob?.cancel()
        _searchState.value = Result.Idle
    }


}