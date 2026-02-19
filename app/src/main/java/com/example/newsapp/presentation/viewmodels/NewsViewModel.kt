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
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException


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

    private val _categoryNewsState =
        MutableStateFlow<Map<String, Result<List<Article>>>>(emptyMap())

    val categoryNewsState = _categoryNewsState.asStateFlow()


    private val _searchState = MutableStateFlow<Result<List<Article>>>(Result.Idle)
    val searchState = _searchState.asStateFlow()

    private val _sourcesByCategory = MutableStateFlow<Map<String, Result<List<Source>>>>(emptyMap())
    val sourcesByCategory = _sourcesByCategory.asStateFlow()

    private val _newsBySourcesMap = MutableStateFlow<Map<String, Result<List<Article>>>>(emptyMap())
    val newsBySourcesMap = _newsBySourcesMap.asStateFlow()

    private var topHeadlinesPage = 1
    private val pageSize = 20
    private var topHeadlinesEndReached = false
    private var topHeadlinesLoading = false
    val cachedTopHeadlines = mutableListOf<Article>()

    private val categoryNewsPages = mutableMapOf<String, Int>()
    private val categoryNewsEndReached = mutableMapOf<String, Boolean>()
    private val categoryNewsLoading = mutableMapOf<String, Boolean>()
    private val cachedCategoryNews = mutableMapOf<String, MutableList<Article>>()

    private val cachedSourcesByCategory = mutableMapOf<String, List<Source>>()

    private val newsBySourcesPages = mutableMapOf<String, Int>()
    private val newsBySourcesEndReached = mutableMapOf<String, Boolean>()
    private val newsBySourcesLoading = mutableMapOf<String, Boolean>()
    private val cachedNewsBySources = mutableMapOf<String, MutableList<Article>>()

    private var searchJob: Job? = null

    private var searchPage = 1
    private var searchPageSize = 50
    private var searchEndReached = false
    private var searchLoading = false
    private val cachedSearch = mutableListOf<Article>()

    private var currentSearchQuery: String = ""


    init {
        getTopHeadlines()
        getCategoryNews("Business")
    }

    fun getTopHeadlines(reset: Boolean = false) {
        if (topHeadlinesLoading || topHeadlinesEndReached) return

        if (reset) {
            topHeadlinesPage = 1
            topHeadlinesEndReached = false
            cachedTopHeadlines.clear()
        }

        viewModelScope.launch {
            topHeadlinesLoading = true
            if (topHeadlinesPage == 1) _newsState.value = Result.Loading

            val result = getTopHeadlinesUseCase(topHeadlinesPage, pageSize)
            when (result) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        topHeadlinesEndReached = true
                    } else {
                        cachedTopHeadlines.addAll(result.data)
                        _newsState.value = Result.Success(cachedTopHeadlines.toList())
                        topHeadlinesPage++
                    }
                }

                is Result.Error -> _newsState.value = Result.Error(result.message)
                else -> {}
            }

            topHeadlinesLoading = false
        }
    }

    fun getNewsBySourcesForFollowing(sourceId: String) {
        cachedNewsBySources[sourceId]?.let {
            _newsBySourcesMap.value = _newsBySourcesMap.value + (sourceId to Result.Success(it))
            return
        }

        viewModelScope.launch {
            _newsBySourcesMap.value = _newsBySourcesMap.value + (sourceId to Result.Loading)
            val result = getNewsBySourcesUseCase(sourceId)
            _newsBySourcesMap.value = when (result) {
                is Result.Success -> {
                    cachedNewsBySources[sourceId] = result.data.toMutableList()
                    _newsBySourcesMap.value + (sourceId to Result.Success(result.data))
                }

                is Result.Error -> _newsBySourcesMap.value + (sourceId to result)
                else -> _newsBySourcesMap.value + (sourceId to Result.Idle)
            }
        }
    }


    fun getCategoryNews(category: String, reset: Boolean = false) {
        val page = categoryNewsPages.getOrDefault(category, 1)
        val endReached = categoryNewsEndReached.getOrDefault(category, false)
        val loading = categoryNewsLoading.getOrDefault(category, false)
        val cached = cachedCategoryNews.getOrPut(category) { mutableListOf() }

        if (loading || endReached) return

        if (reset) {
            categoryNewsPages[category] = 1
            categoryNewsEndReached[category] = false
            cached.clear()
        }

        if (page == 1 && cached.isNotEmpty()) {
            _categoryNewsState.value =
                _categoryNewsState.value + (category to Result.Success(cached))
            return
        }

        viewModelScope.launch {
            categoryNewsLoading[category] = true

            _categoryNewsState.value =
                _categoryNewsState.value + (category to Result.Loading)

            val result = getCategoryNewsUseCase(category, page, pageSize)

            _categoryNewsState.value = when (result) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        categoryNewsEndReached[category] = true
                    } else {
                        cached.addAll(result.data)
                        categoryNewsPages[category] = page + 1
                    }
                    _categoryNewsState.value + (category to Result.Success(cached.toList()))
                }

                is Result.Error ->
                    _categoryNewsState.value + (category to Result.Error(result.message))

                else -> _categoryNewsState.value
            }

            categoryNewsLoading[category] = false
        }
    }

    fun getNewsBySources(sourceId: String, reset: Boolean = false) {
        val page = newsBySourcesPages.getOrDefault(sourceId, 1)
        val endReached = newsBySourcesEndReached.getOrDefault(sourceId, false)
        val loading = newsBySourcesLoading.getOrDefault(sourceId, false)
        val cached = cachedNewsBySources.getOrPut(sourceId) { mutableListOf() }

        if (loading || endReached) return

        if (reset) {
            newsBySourcesPages[sourceId] = 1
            newsBySourcesEndReached[sourceId] = false
            cached.clear()
        }

        if (page == 1 && cached.isNotEmpty()) {
            _newsBySourcesMap.value =
                _newsBySourcesMap.value + (sourceId to Result.Success(cached.toList()))
            return
        }

        viewModelScope.launch {
            newsBySourcesLoading[sourceId] = true

            // Show loading only for first page
            if (page == 1) {
                _newsBySourcesMap.value = _newsBySourcesMap.value + (sourceId to Result.Loading)
            }

            val result = getNewsBySourcesUseCase(sourceId, page, 20)

            _newsBySourcesMap.value = when (result) {
                is Result.Success -> {
                    val newArticles = result.data.filter { newArticle ->
                        cached.none { it.url == newArticle.url }
                    }

                    if (newArticles.isEmpty()) {
                        newsBySourcesEndReached[sourceId] = true
                    } else {
                        cached.addAll(newArticles)
                        newsBySourcesPages[sourceId] = page + 1
                    }

                    _newsBySourcesMap.value + (sourceId to Result.Success(cached.toList()))
                }

                is Result.Error -> _newsBySourcesMap.value + (sourceId to Result.Error(result.message))
                else -> _newsBySourcesMap.value + (sourceId to Result.Idle)
            }

            newsBySourcesLoading[sourceId] = false
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

    fun searchNews(query: String, reset: Boolean = false) {
        if (query.isBlank()) {
            clearSearch()
            return
        }

        if (searchLoading || searchEndReached) return

        if (reset || query != currentSearchQuery) {
            searchJob?.cancel()
            currentSearchQuery = query
            searchPage = 1
            searchEndReached = false
            cachedSearch.clear()
        }
        searchJob = viewModelScope.launch {
            try {
                searchLoading = true

                if (searchPage == 1) {
                    delay(400)
                    _searchState.value = Result.Loading
                }
                val result = searchNewsUseCase(
                    query = query,
                    page = searchPage,
                    pageSize = searchPageSize
                )

                when (result) {
                    is Result.Success -> {
                        val newArticles = result.data.filter { newArticle ->
                            cachedSearch.none {
                                it.url == newArticle.url
                            }
                        }

                        if (newArticles.isEmpty()){
                            searchEndReached=true
                        }else{
                            cachedSearch.addAll(newArticles)
                            searchPage++
                        }
                        _searchState.value= Result.Success(cachedSearch.toList())
                    }
                    is Result.Error -> {
                        _searchState.value= Result.Error(result.message)
                    }
                    else->{}
                }
            }catch (e: Exception) {
                if (e !is CancellationException) {
                    _searchState.value =
                        Result.Error("Failed to search news.")
                }
            } finally {
                searchLoading = false
            }
        }
    }

    fun clearSearch() {
        searchJob?.cancel()
        searchPage = 1
        searchEndReached = false
        searchLoading = false
        currentSearchQuery = ""
        cachedSearch.clear()
        _searchState.value = Result.Idle
    }


}