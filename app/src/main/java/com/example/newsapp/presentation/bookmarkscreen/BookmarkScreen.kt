package com.example.newsapp.presentation.bookmarkscreen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsapp.navigation.Routes
import com.example.newsapp.presentation.mainscreen.homescreen.components.NewsCard
import com.example.newsapp.presentation.mainscreen.homescreen.components.ShimmeredNewsCard
import com.example.newsapp.presentation.utils.getRelativeTime
import com.example.newsapp.presentation.utils.openWebsite
import com.example.newsapp.presentation.utils.shareUrlIntent
import com.example.newsapp.presentation.viewmodels.BookmarkViewModel
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.PlayFairDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val bookmarkState by bookmarkViewModel.uiState.collectAsState()

    val shareLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    LaunchedEffect(bookmarkState.message) {
        bookmarkState.message?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            bookmarkViewModel.clearMessage()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Bookmarks",
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

            if (bookmarkState.bookmarks.isEmpty()) {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "No saved articles",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal,
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            modifier = Modifier
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF02040D),
                                contentColor = Color.White
                            ),
                            onClick = {
                                navHostController.navigate(Routes.MainScreen){
                                    popUpTo(Routes.BookmarkScreen){
                                        inclusive = true
                                    }
                                    launchSingleTop=true
                                }
                            }) {
                            Text(
                                text = "Browse News",
                                fontFamily = InterDisplay,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(paddingValues)
                ) {
                    if (bookmarkState.isLoading) {
                        items(8) {
                            ShimmeredNewsCard(true)
                        }
                    } else {
                        items(bookmarkState.bookmarks) { article ->
                            val isBookmarked = bookmarkState.bookmarks.any { it.url == article.url }

                            Box(
                                modifier = Modifier.padding(8.dp)
                            ) {
                                NewsCard(
                                    isBookmarked = isBookmarked,
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
            }
        }
    }


}