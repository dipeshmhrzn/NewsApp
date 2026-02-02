package com.example.newsapp.presentation.mainscreen.sourcescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.domain.util.Result
import com.example.newsapp.presentation.mainscreen.components.MenuItems
import com.example.newsapp.presentation.mainscreen.homescreen.components.ShimmeredNewsCard
import com.example.newsapp.presentation.mainscreen.homescreen.components.ShimmeredTopHeadlineCard
import com.example.newsapp.presentation.mainscreen.sourcescreen.components.ShimmeredSourceNewsCard
import com.example.newsapp.presentation.viewmodels.NewsViewModel
import com.example.newsapp.presentation.mainscreen.sourcescreen.components.SourceNewsCard
import com.example.newsapp.presentation.utils.getRelativeTime
import com.example.newsapp.presentation.utils.openWebsite
import com.example.newsapp.presentation.utils.shareUrl
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.PlayFairDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourcesDetailScreen(
    sourceId: String,
    navHostController: NavHostController,
    viewModel: NewsViewModel
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val context = LocalContext.current

    LaunchedEffect(sourceId) {
        viewModel.getNewsBySources(sourceId)
    }

    val newsBySources by viewModel.newsBySources.collectAsState()

    val sourceTitle = when (val state = newsBySources) {
        is Result.Success -> state.data.firstOrNull()?.source?.name ?: "News"
        else -> "News"
    }
    var isMenuVisible by remember { mutableStateOf(false) }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
    Scaffold(
        topBar = {
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
                                color = Color(0xFF737373),
                                shape = CircleShape
                            )
                            .clickable {}
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.StarBorder,
                            contentDescription = "Following",
                            tint = Color(0xFF737373)
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
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {

            when (val state = newsBySources) {
                is Result.Success -> {
                    val articles = state.data
                    items(articles) { article ->
                        Box(
                            modifier = Modifier.padding(8.dp)
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
                        ShimmeredSourceNewsCard(true)
                    }
                }

                is Result.Error -> {
                    items(5) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(150.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF737373).copy(alpha = .1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = state.message.toString(),
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
    if (isMenuVisible) {
        MenuItems(
            article = selectedArticle!!,
            onDismiss = {
                isMenuVisible = false
            },
            onSaveClick = {},
            onShareClick = { article ->
                shareUrl(context, article.url)
            },
            onRedirectClick = {article ->
                openWebsite(context, article.url)
            }
        )
    }}
}

