package com.example.newsapp.presentation.bookmarkscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.newsapp.presentation.mainscreen.homescreen.components.NewsCard
import com.example.newsapp.presentation.utils.getRelativeTime
import com.example.newsapp.presentation.utils.openWebsite
import com.example.newsapp.presentation.utils.shareUrlIntent
import com.example.newsapp.presentation.viewmodels.BookmarkViewModel

@Composable
fun BookmarkScreen(bookmarkViewModel: BookmarkViewModel = hiltViewModel()) {

    val uiState by bookmarkViewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        // Show a loading indicator
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // Show list of bookmarks
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(uiState.bookmarks) { article ->
                NewsCard(
                    urlToImage = article.urlToImage,
                    title = article.title,
                    sourceName = article.source.name,
                    onCardClick = {
                    },
                    onShareClick = {
                    },
                    onBookmarkClick = {},
                    publishedAt = getRelativeTime(article.publishedAt)
                )
            }
        }
    }

}