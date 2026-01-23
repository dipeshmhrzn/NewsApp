package com.example.newsapp.presentation.mainscreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.presentation.mainscreen.components.OpenWebsiteHandler
import com.example.newsapp.presentation.mainscreen.components.ShareHandler
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.PlayFairDisplay

@Composable
fun NewsDetailScreen(navHostController: NavHostController) {
    Scaffold(
        bottomBar = {

            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                containerColor = Color(0xFF737373).copy(alpha = .1f),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                ) {

                    TextButton(
                        onClick = {},
                        border = BorderStroke(1.dp, color = Color(0xFF02040D))
                    ) {
                        Text(
                            text = "CNBC",
                            color = Color(0xFF4E4B66),
                            fontFamily = InterDisplay,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    ShareHandler(url = "https://google.com") { onShare ->
                        IconButton(onClick = onShare) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = null,
                                tint = Color(0xFF4E4B66),
                                modifier = Modifier.size(20.dp)

                            )
                        }
                    }

                    OpenWebsiteHandler(url = "https://google.com") { onRedirect ->
                        IconButton(onClick = onRedirect) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                contentDescription = null,
                                tint = Color(0xFF4E4B66),
                                modifier = Modifier.size(20.dp)

                            )
                        }
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Outlined.BookmarkBorder,
                            contentDescription = null,
                            tint = Color(0xFF4E4B66),
                            modifier = Modifier.size(20.dp)

                        )
                    }
                }
            }
        },
        containerColor = Color(0xFFE5E5E5),
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(R.drawable.onboarding1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pia Singh",
                fontSize = 18.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF4E4B66)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Stocks rebound from big sell-off after Trump rules out military action on Greenland: Live updates - CNBC",
                fontSize = 28.sp,
                fontFamily = PlayFairDisplay,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Published At : 2026-01-22",
                fontSize = 18.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF4E4B66)
            )
            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                thickness = 1.dp,
                color = Color(0xFF4E4B66).copy(alpha = .5f)
            )
            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = "Major U.S. stock averages immediately jumped after President Donald Trump said he would no longer impose his new Europe tariffs that were set to begin Feb. 1.",
                fontSize = 20.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))

             Text(
                    text = "The Mets make another splash, acquiring All-Star starter Freddy Peralta and swingman Tobias Myers from the Brewers for prospects Jett Williams and Brandon Sproat. The teams announced the blockbuster … [+12716 chars]",
                    fontSize = 20.sp,
                    fontFamily = InterDisplay,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Justify,
                    lineHeight = 24.sp

                )

        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NewsDetailsPrev() {
    NewsAppTheme {
        NewsDetailScreen(rememberNavController())
    }
}