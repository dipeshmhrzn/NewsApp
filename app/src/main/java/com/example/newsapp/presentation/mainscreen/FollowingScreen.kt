package com.example.newsapp.presentation.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.presentation.mainscreen.components.NewsCard
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.PlayFairDisplay

@Composable
fun FollowingScreen(navHostController: NavHostController) {

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
        items(4) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(10.dp))
                            .clickable{}
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "The Himalayan Times",
                            fontFamily = InterDisplay,
                            fontSize = 18.sp
                        )

                        Box(
                            modifier = Modifier
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
                    }
                    repeat(2) {
                        NewsCard(onCardClick = {})
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(8.dp)
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
                                text = "Read more from The Himalayan Times",
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
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

        }

    }
}

