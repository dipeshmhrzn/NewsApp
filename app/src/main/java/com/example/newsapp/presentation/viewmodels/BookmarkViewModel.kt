package com.example.newsapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.usecase.bookmarkusecase.AddBookmark
import com.example.newsapp.domain.usecase.bookmarkusecase.GetBookmarks
import com.example.newsapp.domain.usecase.bookmarkusecase.IsBookmarked
import com.example.newsapp.domain.usecase.bookmarkusecase.RemoveBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class BookmarkUiState(
    val bookmarks: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null
)


@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarks: GetBookmarks,
    private val addBookmark: AddBookmark,
    private val removeBookmark: RemoveBookmark,
    private val isBookmarked: IsBookmarked
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookmarkUiState(isLoading = true))
    val uiState: StateFlow<BookmarkUiState> = _uiState.asStateFlow()

    // StateFlow for all bookmarks
    val bookmarks: StateFlow<List<Article>> = getBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())


    init {
        fetchBookmarks()
    }

    private fun fetchBookmarks() {
        viewModelScope.launch {
            getBookmarks().collect { list ->
                _uiState.value = _uiState.value.copy(
                    bookmarks = list,
                    isLoading = false
                )
            }
        }
    }

    fun toggleBookmark(article: Article) {
        viewModelScope.launch {
            try {
                val bookmarked = isBookmarked(article.url)
                if (bookmarked) {
                    removeBookmark(article)
                    _uiState.value = _uiState.value.copy(message = "Removed from bookmarks")
                } else {
                    addBookmark(article)
                    _uiState.value = _uiState.value.copy(message = "Added to bookmarks")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(message = "Failed to update bookmark")
            }
        }
    }

    suspend fun isBookmarkedArticle(url: String): Boolean {
        return isBookmarked(url)
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }

}