package com.example.newsapp.presentation.mainscreen.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.presentation.mainscreen.homescreen.components.TopHeadLinesCard
import com.example.newsapp.presentation.utils.getRelativeTime
import com.example.newsapp.presentation.utils.openWebsite
import com.example.newsapp.ui.theme.PlayFairDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTopHeadlineScreen(
    navHostController: NavHostController,
    articles: List<Article>
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val context = LocalContext.current

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

        if (articles.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No articles available",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(articles) { article ->
                    Box(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        TopHeadLinesCard(
                            screenWidth = screenWidth,
                            onCardClick = {
                                openWebsite(context, article.url)
                            },
                            onMenuClick = {},
                            urlToImage = article.urlToImage,
                            author = article.author ?: "",
                            title = article.title,
                            sourceName = article.source.name,
                            publishedAt = getRelativeTime(article.publishedAt)
                        )
                    }
                }
            }
        }
    }
}