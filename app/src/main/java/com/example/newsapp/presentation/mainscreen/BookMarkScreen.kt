package com.example.newsapp.presentation.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.presentation.mainscreen.components.NewsCard

@Composable
fun BookmarkScreen(navHostController: NavHostController) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),

        ) {
        items(20) {

            Box(
                modifier = Modifier.padding(8.dp)
            ) {
                NewsCard(onCardClick = { })
            }
        }
    }
}