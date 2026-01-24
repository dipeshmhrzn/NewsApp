package com.example.newsapp.presentation.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.navigation.Routes
import com.example.newsapp.presentation.mainscreen.components.TopHeadLinesCard
import com.example.newsapp.ui.theme.PlayFairDisplay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTopHeadlineScreen(navHostController: NavHostController) {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

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
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(20) {
                Box(
                    modifier = Modifier.padding(8.dp)
                ) {
                    TopHeadLinesCard(
                        screenWidth,
                        onCardClick = {
                            navHostController.navigate(Routes.NewsDetailScreen)
                        },
                        onMenuClick = {

                        }
                    )
                }
            }

        }
    }
}