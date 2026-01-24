package com.example.newsapp.presentation.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.presentation.mainscreen.components.NewsCard
import com.example.newsapp.presentation.mainscreen.components.SourceCard
import com.example.newsapp.ui.theme.InterDisplay

@Composable
fun SourceScreen(navHostController: NavHostController) {

    val newsCategories = listOf(
        "Business",
        "Technology",
        "Entertainment",
        "General",
        "Health",
        "Science",
        "Sports"
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(color = Color(0xFFE5E5E5))
    ) {
        items(newsCategories) { category ->
            Box(
                modifier = Modifier.fillMaxSize().background(Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp).padding(start = 16.dp)
                ) {
                    Text(
                        text = category,
                        fontFamily = InterDisplay,
                        fontSize = 20.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(10) {
                            SourceCard()
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}