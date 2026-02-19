package com.example.newsapp.presentation.mainscreen.homescreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.util.Result
import com.example.newsapp.presentation.mainscreen.homescreen.components.NewsCard
import com.example.newsapp.presentation.mainscreen.homescreen.components.ShimmeredNewsCard
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
@Composable
fun HomeScreen(
    onSeeAll: () -> Unit,
    onMenuClick: (item: Article) -> Unit,
    viewModel: NewsViewModel,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val shareLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }

    val newsState by viewModel.newsState.collectAsState()
    val bookmarkState by bookmarkViewModel.uiState.collectAsState()
    val categoryNewsMap by viewModel.categoryNewsState.collectAsState()

    val newsCategories = listOf(
        "Business",
        "Technology",
        "Entertainment",
        "General",
        "Health",
        "Science",
        "Sports"
    )

    var selectedCategory by remember { mutableStateOf("Business") }
    val categoryNewsState = categoryNewsMap[selectedCategory] ?: Result.Idle

    val listState = rememberLazyListState()
    val topHeadlinesState = rememberLazyListState()

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.9f


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
        if (lastVisibleItem >= totalItems - 5) {
            viewModel.getTopHeadlines()
        }
    }

    LaunchedEffect(listState.firstVisibleItemIndex, listState.layoutInfo.totalItemsCount) {
        val lastVisibleItem = listState.firstVisibleItemIndex +
                listState.layoutInfo.visibleItemsInfo.size
        val totalItems = listState.layoutInfo.totalItemsCount
        if (lastVisibleItem >= totalItems - 5) {
            viewModel.getCategoryNews(selectedCategory)
        }
    }


    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Top Headlines",
                    fontSize = 30.sp,
                    fontFamily = PlayFairDisplay,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = onSeeAll) {
                    Text(
                        text = "See all",
                        fontSize = 20.sp,
                        fontFamily = InterDisplay,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF737373)
                    )
                }
            }

            LazyRow(state = topHeadlinesState) {
                when (val state = newsState) {
                    is Result.Success -> {
                        val articles = state.data
                        items(articles) { item ->
                            Box(
                                modifier = Modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                            ) {
                                TopHeadLinesCard(
                                    screenWidth = cardWidth,
                                    onCardClick = { openWebsite(context, item.url) },
                                    onMenuClick = { onMenuClick(item) },
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
                        items(5) {
                            ShimmeredTopHeadlineCard(true)
                        }
                    }

                    is Result.Error -> {
                        items(5) {
                            Box(
                                modifier = Modifier
                                    .width(cardWidth)
                                    .padding(16.dp)
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF737373).copy(alpha = .1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Error fetching top headlines",
                                    fontSize = 18.sp,
                                    fontFamily = InterDisplay,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                        }
                    }
                }
            }
        }
        stickyHeader {
            val isStuck by remember {
                derivedStateOf { listState.firstVisibleItemIndex > 0 }
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (isStuck) Color(0xFFFFFFFF)
                        else Color.Transparent
                    )
                    .padding(vertical = 8.dp)
                    .zIndex(1f),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(newsCategories) { category ->
                    val isSelected = category == selectedCategory

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                if (isSelected) Color(0xFF02040D)
                                else Color.Transparent
                            )
                            .border(
                                border = BorderStroke(1.dp, Color(0xFF02040D)),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                selectedCategory = category
                                viewModel.getCategoryNews(category)
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            fontSize = 18.sp,
                            fontFamily = InterDisplay,
                            fontWeight = FontWeight.Normal,
                            color = if (isSelected) Color.White else Color(0xFF02040D)
                        )
                    }
                }
            }
        }
        when (val state = categoryNewsState) {
            is Result.Success -> {
                val categoryNews = state.data
                items(categoryNews) { item ->
                    Box(
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    ) {
                        val isBookmarked = bookmarkState.bookmarks.any { it.url == item.url }

                        NewsCard(
                            isBookmarked = isBookmarked,
                            urlToImage = item.urlToImage,
                            title = item.title,
                            sourceName = item.source.name,
                            onCardClick = {
                                openWebsite(context, item.url)
                            },
                            onShareClick = {
                                shareLauncher.launch(shareUrlIntent(item.url))
                            },
                            onBookmarkClick = {
                                bookmarkViewModel.toggleBookmark(item)
                            },
                            publishedAt = getRelativeTime(item.publishedAt)
                        )
                    }
                }
            }

            Result.Idle, Result.Loading -> {
                items(8) {
                    ShimmeredNewsCard(true)
                }
            }

            is Result.Error -> {
                items(8) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF737373).copy(alpha = .1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error fetching $selectedCategory news",
                            fontSize = 18.sp,
                            fontFamily = InterDisplay,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
            }
        }
    }
}