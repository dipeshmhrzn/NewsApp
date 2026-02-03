package com.example.newsapp.presentation.searchscreen

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.newsapp.domain.util.Result
import com.example.newsapp.presentation.mainscreen.homescreen.components.NewsCard
import com.example.newsapp.presentation.mainscreen.homescreen.components.ShimmeredNewsCard
import com.example.newsapp.presentation.utils.findActivity
import com.example.newsapp.presentation.utils.getRelativeTime
import com.example.newsapp.presentation.utils.openWebsite
import com.example.newsapp.presentation.utils.shareUrlIntent
import com.example.newsapp.presentation.viewmodels.BookmarkViewModel
import com.example.newsapp.presentation.viewmodels.NewsViewModel
import com.example.newsapp.ui.theme.InterDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    newsViewModel: NewsViewModel = hiltViewModel(),
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {

    var query by remember { mutableStateOf("") }

    val searchState by newsViewModel.searchState.collectAsState()

    val focusRequester = remember { FocusRequester() }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val context = LocalContext.current

    val shareLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    val bookmarkState by bookmarkViewModel.uiState.collectAsState()

    LaunchedEffect(bookmarkState.message) {
        bookmarkState.message?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            bookmarkViewModel.clearMessage()
        }
    }


    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                TextField(
                    value = query,
                    onValueChange = {
                        query = it
                        newsViewModel.searchNews(it)
                    },
                    placeholder = {
                        Text(
                            text = "Search news...",
                            fontFamily = InterDisplay,
                            color = Color(0xFFBBBBBB),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Light
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color(0xFF4E4B66),
                            modifier = Modifier.size(25.dp)
                        )
                    },
                    trailingIcon = {
                        if (query.isNotBlank()) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cancel Search",
                                tint = Color(0xFF4E4B66),
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        query = ""
                                        newsViewModel.clearSearch()
                                    })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .focusRequester(focusRequester),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF737373).copy(alpha = .1f),
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }
        },
        containerColor = Color(0xFFFFFFFF),
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            when (val state = searchState) {

                is Result.Success -> {
                    val articles = state.data
                    if (articles.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 150.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No results found for \"$query\"",
                                    fontSize = 20.sp,
                                    fontFamily = InterDisplay,
                                    fontWeight = FontWeight.Normal,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        items(articles) { article ->
                            val isBookmarked = bookmarkState.bookmarks.any { it.url == article.url }

                            Box(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                NewsCard(
                                    isBookmarked=isBookmarked,
                                    urlToImage = article.urlToImage,
                                    title = article.title,
                                    sourceName = article.source.name,
                                    onCardClick = {
                                        openWebsite(context, article.url)
                                    },
                                    onShareClick = {
                                        shareLauncher.launch(shareUrlIntent(article.url))
                                    },
                                    onBookmarkClick = {
                                        bookmarkViewModel.toggleBookmark(article)
                                    },
                                    publishedAt = getRelativeTime(article.publishedAt)
                                )
                            }
                        }
                    }
                }

                is Result.Idle -> {
                    if (query.isBlank()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Spacer(modifier = Modifier.height(150.dp))
                                Text(
                                    text = "Search news...",
                                    fontSize = 20.sp,
                                    fontFamily = InterDisplay,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                Result.Loading -> {
                    items(8) {
                        ShimmeredNewsCard(true)
                    }
                }

                is Result.Error -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Spacer(modifier = Modifier.height(150.dp))
                            Text(
                                text = state.message.toString(),
                                fontSize = 20.sp,
                                fontFamily = InterDisplay,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }

        }

    }
}