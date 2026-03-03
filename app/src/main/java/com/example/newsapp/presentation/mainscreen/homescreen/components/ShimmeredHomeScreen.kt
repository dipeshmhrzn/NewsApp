package com.example.newsapp.presentation.mainscreen.homescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShimmeredHomeScreen(isLoading: Boolean = false) {
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Spacer(
                                modifier = Modifier
                                    .width(180.dp)
                                    .height(30.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .placeholder(
                                        true,
                                        highlight = PlaceholderHighlight.shimmer(
                                            highlightColor = Color(0xFF737373).copy(alpha = .1f)
                                        )
                                    )
                            )
                        },
                        navigationIcon = {
                            Spacer(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .placeholder(
                                        true,
                                        highlight = PlaceholderHighlight.shimmer(
                                            highlightColor = Color(0xFF737373).copy(alpha = .1f)
                                        )
                                    )
                            )
                        },
                        actions = {
                            Spacer(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .placeholder(
                                        true,
                                        highlight = PlaceholderHighlight.shimmer(
                                            highlightColor = Color(0xFF737373).copy(alpha = .1f)
                                        )
                                    )
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color(0xFFFFFFFF)
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                },
                containerColor = Color(0xFFFFFFFF),
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    item {
                        ShimmeredTopHeadlineCard(
                            isLoading = true,
                            cardWidth = LocalConfiguration.current.screenWidthDp.dp
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(3) {
                                Spacer(
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(45.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                        .placeholder(
                                            true,
                                            highlight = PlaceholderHighlight.shimmer(
                                                highlightColor = Color(0xFF737373).copy(alpha = .1f)
                                            )
                                        )
                                )
                            }
                        }
                        repeat(3) {
                            ShimmeredNewsCard(true)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Shimmer() {
    ShimmeredHomeScreen(true)
}