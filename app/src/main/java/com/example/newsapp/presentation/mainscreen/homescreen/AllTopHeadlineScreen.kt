package com.example.newsapp.presentation.mainscreen.homescreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.util.Result
import com.example.newsapp.presentation.mainscreen.components.MenuItems
import com.example.newsapp.presentation.mainscreen.homescreen.components.ShimmeredTopHeadlineCard
import com.example.newsapp.presentation.mainscreen.homescreen.components.TopHeadLinesCard
import com.example.newsapp.presentation.utils.getRelativeTime
import com.example.newsapp.presentation.utils.openWebsite
import com.example.newsapp.presentation.utils.shareUrlIntent
import com.example.newsapp.presentation.viewmodels.BookmarkViewModel
import com.example.newsapp.presentation.viewmodels.NewsViewModel
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.PlayFairDisplay

@SuppressLint("FrequentlyChangingValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTopHeadlineScreen(
    navHostController: NavHostController,
    viewModel: NewsViewModel,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {

    val newsState by viewModel.newsState.collectAsState()

    val topHeadlinesState = rememberLazyListState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val context = LocalContext.current
    val shareLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    var isMenuVisible by remember { mutableStateOf(false) }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    val bookmarkState by bookmarkViewModel.uiState.collectAsState()

    LaunchedEffect(bookmarkState.message) {
        bookmarkState.message?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            bookmarkViewModel.clearMessage()
        }
    }

    LaunchedEffect(
        topHeadlinesState.firstVisibleItemIndex,
        topHeadlinesState.layoutInfo.totalItemsCount
    ) {
        val lastVisibleItem = topHeadlinesState.firstVisibleItemIndex +
                topHeadlinesState.layoutInfo.visibleItemsInfo.size
        val totalItems = topHeadlinesState.layoutInfo.totalItemsCount
        if (lastVisibleItem >= totalItems - 3) {
            viewModel.getTopHeadlines()
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Top Headlines",
                            fontFamily = PlayFairDisplay,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navHostController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color(0xFFFFFFFF)
                    )
                )
            },
            containerColor = Color(0xFFFFFFFF)
        ) { paddingValues ->
            when (val state = newsState) {
                is Result.Success, Result.Loading, Result.Idle -> {
                    LazyColumn(
                        state = topHeadlinesState,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        when (state) {
                            is Result.Success -> {
                                items(state.data, key = { it.url }) { item ->
                                    Box(
                                        modifier = Modifier.padding(
                                            start = 8.dp,
                                            end = 8.dp,
                                            bottom = 8.dp
                                        )
                                    ) {
                                        TopHeadLinesCard(
                                            screenWidth = screenWidth,
                                            onCardClick = {
                                                openWebsite(context, item.url)
                                            },
                                            onMenuClick = {
                                                selectedArticle = item
                                                isMenuVisible = !isMenuVisible
                                            },
                                            urlToImage = item.urlToImage,
                                            author = item.author ?: "",
                                            title = item.title,
                                            sourceName = item.source.name,
                                            publishedAt = getRelativeTime(item.publishedAt)
                                        )
                                    }
                                }
                            }

                            Result.Idle, Result.Loading -> {
                                items(8) {
                                    Box(modifier = Modifier.padding(8.dp)) {
                                        ShimmeredTopHeadlineCard(
                                            isLoading = true,
                                            cardWidth = screenWidth
                                        )
                                    }
                                }
                            }

                            else -> {}
                        }
                    }
                }

                is Result.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error occurred!",
                            fontSize = 22.sp,
                            fontFamily = InterDisplay,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }
        }
        if (isMenuVisible) {
            MenuItems(
                article = selectedArticle!!,
                isBookmarked = bookmarkState.bookmarks.any { it.url == selectedArticle!!.url },
                onDismiss = {
                    isMenuVisible = false
                },
                onSaveClick = { article ->
                    bookmarkViewModel.toggleBookmark(article)
                },
                onShareClick = { article ->
                    shareLauncher.launch(shareUrlIntent(article.url))
                },
                onRedirectClick = { article ->
                    openWebsite(context, article.url)
                }
            )
        }
    }
}