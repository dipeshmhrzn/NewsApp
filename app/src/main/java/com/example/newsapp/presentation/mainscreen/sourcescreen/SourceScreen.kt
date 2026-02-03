package com.example.newsapp.presentation.mainscreen.sourcescreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.newsapp.domain.util.Result
import com.example.newsapp.navigation.Routes
import com.example.newsapp.presentation.mainscreen.sourcescreen.components.SourceCard
import com.example.newsapp.presentation.viewmodels.FollowViewModel
import com.example.newsapp.presentation.viewmodels.NewsViewModel
import com.example.newsapp.ui.theme.InterDisplay
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun SourceScreen(
    onSourceClick: (String) -> Unit = {},
    viewModel: NewsViewModel,
    followViewModel: FollowViewModel = hiltViewModel()
) {

    val newsCategories = listOf(
        "Business",
        "Technology",
        "Entertainment",
        "General",
        "Health",
        "Science",
        "Sports"
    )

    val sourcesByCategory by viewModel.sourcesByCategory.collectAsState()

    val context = LocalContext.current

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val followedSourceIds by followViewModel
        .followedSourceIds
        .collectAsState()

    LaunchedEffect(Unit) {
        newsCategories.forEach { category ->
            viewModel.getSources(category)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE5E5E5))
    ) {
        items(newsCategories) { category ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = category,
                        fontFamily = InterDisplay,
                        fontSize = 20.sp,
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        when (val state = sourcesByCategory[category]) {
                            is Result.Success -> {
                                items(state.data) { source ->
                                    val isFollowed = followedSourceIds.contains(source.id)

                                    SourceCard(
                                        source = source.name,
                                        isFollowed = isFollowed,
                                        onSourceClick = {
                                            onSourceClick(source.id)
                                        },
                                        onFollowClick = {
                                            if (isFollowed) {
                                                followViewModel.unfollowSource(source.id)
                                            } else {
                                                followViewModel.followSource(source.id)
                                            }
                                        }
                                    )
                                }
                            }

                            Result.Idle, Result.Loading -> {
                                items(6) {
                                    Spacer(
                                        modifier = Modifier
                                            .width(150.dp)
                                            .height(50.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .placeholder(
                                                true,
                                                highlight = PlaceholderHighlight.shimmer(
                                                    highlightColor = Color(0xFF737373).copy(alpha = .1f)
                                                )
                                            )
                                    )
                                }
                            }

                            is Result.Error -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .width(screenWidth)
                                            .height(50.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0xFF737373).copy(alpha = .1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Unable to fetch sources",
                                            fontSize = 18.sp,
                                            fontFamily = InterDisplay,
                                            fontWeight = FontWeight.Normal,
                                        )
                                    }
                                }
                            }

                            else -> {

                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}