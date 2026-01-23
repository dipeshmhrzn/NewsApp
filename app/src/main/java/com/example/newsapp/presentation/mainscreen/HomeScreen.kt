package com.example.newsapp.presentation.mainscreen

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.presentation.mainscreen.components.NewsCard
import com.example.newsapp.presentation.mainscreen.components.TopHeadLinesCard
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.PlayFairDisplay

@Composable
fun HomeScreen(navHostController: NavHostController) {

    val context = LocalContext.current

    val newsCategories = listOf(
        "All",
        "Business",
        "Technology",
        "Entertainment",
        "General",
        "Health",
        "Science",
        "Sports"
    )

    var selectedCategory by remember { mutableStateOf("All") }

    val listState = rememberLazyListState()


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
                TextButton(onClick = {}) {
                    Text(
                        text = "See all",
                        fontSize = 20.sp,
                        fontFamily = InterDisplay,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF737373)
                    )
                }
            }
            LazyRow {
                items(10) {
                    TopHeadLinesCard()
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
                        if (isStuck) Color(0xFFE5E5E5)
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
        items(20) {

            NewsCard()
        }
    }
}


fun openWebsite(context: Context, url: String) {
    val builder = CustomTabsIntent.Builder()
    builder.setShowTitle(true)
    builder.setInstantAppsEnabled(true)

    val customTabsIntent = builder.build()
    try {
        customTabsIntent.launchUrl(context, url.toUri())
    } catch (e: ActivityNotFoundException) {
        try {
            val fallbackIntent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(fallbackIntent)
        } catch (ex: Exception) {
            Toast.makeText(context, "No browser found to open link", Toast.LENGTH_SHORT).show()
        }
    }
}



