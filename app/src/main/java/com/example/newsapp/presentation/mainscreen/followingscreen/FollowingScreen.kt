package com.example.newsapp.presentation.mainscreen.followingscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.newsapp.domain.util.Result
import com.example.newsapp.presentation.mainscreen.followingscreen.components.FollowingCard
import com.example.newsapp.presentation.mainscreen.sourcescreen.components.ShimmeredSourceNewsCard
import com.example.newsapp.presentation.viewmodels.FollowViewModel
import com.example.newsapp.presentation.viewmodels.NewsViewModel
import com.example.newsapp.ui.theme.InterDisplay

@Composable
fun FollowingScreen(
    onNavigateToSource: () -> Unit = {},
    onReadMoreClick: (String) -> Unit = {},
    followViewModel: FollowViewModel = hiltViewModel(),
    newsViewModel: NewsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val followedSourceIds by followViewModel
        .followedSourceIds
        .collectAsState()

    val newsBySourcesMap by newsViewModel.newsBySourcesMap.collectAsState()

    LaunchedEffect(followedSourceIds) {
        followedSourceIds.forEach { sourceId ->
            newsViewModel.getNewsBySourcesForFollowing(sourceId)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE5E5E5))
    ) {
        item {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {}

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFE5E5E5))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Saved News",
                        fontFamily = InterDisplay,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color(0xFF4E4B66)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (followedSourceIds.isEmpty()) {
            item {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(modifier = Modifier.height(250.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Your followed sources list is empty !!",
                            fontFamily = InterDisplay,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                onNavigateToSource()
                            },
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF02040D)
                            )
                        ) {
                            Text(
                                text = "Browse Sources",
                                fontFamily = InterDisplay,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }

        followedSourceIds.toList().forEach { sourceId ->

            val isFollowed = followedSourceIds.contains(sourceId)
            val result = newsBySourcesMap[sourceId]

            when (result) {
                is Result.Loading -> {
                    // Show shimmer cards
                    repeat(3) {
                        item { ShimmeredSourceNewsCard(true) }
                    }
                }

                is Result.Success -> {
                    item {
                        FollowingCard(
                            isFollowed = isFollowed,
                            articles = result.data,
                            onFollowClick = {
                                if (isFollowed) {
                                    followViewModel.unfollowSource(sourceId)
                                } else {
                                    followViewModel.followSource(sourceId)
                                }
                            }, onReadMoreClick = {
                                onReadMoreClick(sourceId)
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                    }
                }

                is Result.Error -> {
                    item {
                        Text(
                            text = "Failed to load news for $sourceId",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                else -> {}
            }
        }

    }
}

