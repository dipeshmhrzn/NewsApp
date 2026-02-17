package com.example.newsapp.presentation.mainscreen.sourcescreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.util.Result
import com.example.newsapp.presentation.mainscreen.components.MenuItems
import com.example.newsapp.presentation.mainscreen.sourcescreen.components.ShimmeredSourceNewsCard
import com.example.newsapp.presentation.mainscreen.sourcescreen.components.SourceNewsCard
import com.example.newsapp.presentation.utils.getRelativeTime
import com.example.newsapp.presentation.utils.openWebsite
import com.example.newsapp.presentation.utils.shareUrlIntent
import com.example.newsapp.presentation.viewmodels.BookmarkViewModel
import com.example.newsapp.presentation.viewmodels.FollowViewModel
import com.example.newsapp.presentation.viewmodels.NewsViewModel
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.PlayFairDisplay

@SuppressLint("FrequentlyChangingValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourcesDetailScreen(
    sourceId: String,
    navHostController: NavHostController,
    viewModel: NewsViewModel,
    followViewModel: FollowViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()

    val shareLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    val followedSourceIds by followViewModel
        .followedSourceIds
        .collectAsState()

    val newsBySourcesMap by viewModel.newsBySourcesMap.collectAsState()
    val newsBySources = newsBySourcesMap[sourceId] ?: Result.Loading

    val sourceTitle = when (val state = newsBySources) {
        is Result.Success -> state.data.firstOrNull()?.source?.name ?: "News"
        else -> "News"
    }
    var isMenuVisible by remember { mutableStateOf(false) }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    val bookmarkState by bookmarkViewModel.uiState.collectAsState()

    LaunchedEffect(sourceId) {
        viewModel.getNewsBySources(sourceId)
    }

    LaunchedEffect(bookmarkState.message) {
        bookmarkState.message?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            bookmarkViewModel.clearMessage()
        }
    }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.layoutInfo.totalItemsCount) {
        val lastVisibleItem =
            listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size
        val totalItems = listState.layoutInfo.totalItemsCount
        if (lastVisibleItem >= totalItems - 5) {
            viewModel.getNewsBySources(sourceId)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                val isFollowed = followedSourceIds.contains(sourceId)
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = sourceTitle,
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
                    }, actions = {
                        Box(
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .size(32.dp)
                                .clip(shape = CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = if (isFollowed) Color.Black else Color(0xFF737373),
                                    shape = CircleShape
                                )
                                .clickable {
                                    if (isFollowed) {
                                        followViewModel.unfollowSource(sourceId)
                                    } else {
                                        followViewModel.followSource(sourceId)
                                    }
                                }
                                .padding(3.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isFollowed)
                                    Icons.Filled.Star
                                else
                                    Icons.Outlined.StarBorder,
                                contentDescription = "Following",
                                tint = if (isFollowed) Color.Black else Color(0xFF737373)
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
            when (val state = newsBySources) {
                is Result.Success, Result.Loading, Result.Idle -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        when (state) {
                            is Result.Success -> {
                                items(state.data, key = { it.url }) { article ->
                                    Box(
                                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                                    ) {
                                        SourceNewsCard(
                                            onCardClick = {
                                                openWebsite(context, article.url)
                                            },
                                            onMenuClick = {
                                                selectedArticle = article
                                                isMenuVisible = !isMenuVisible
                                            },
                                            urlToImage = article.urlToImage,
                                            author = article.author ?: "",
                                            title = article.title,
                                            publishedAt = getRelativeTime(article.publishedAt)
                                        )
                                    }
                                }
                            }

                            Result.Idle, Result.Loading -> {
                                items(8) {
                                    Box(modifier = Modifier.padding(8.dp)) {
                                        ShimmeredSourceNewsCard(true)
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
                            text = "Unable to fetch news from source :\"$sourceId\"",
                            fontSize = 18.sp,
                            fontFamily = InterDisplay,
                            fontWeight = FontWeight.Normal,
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

