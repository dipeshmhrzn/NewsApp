package com.example.newsapp.presentation.mainscreen

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.Routes
import com.example.newsapp.presentation.mainscreen.components.BottomBar
import com.example.newsapp.presentation.mainscreen.components.TopAppBar

@Composable
fun MainScreen(navHostController: NavHostController) {

    val innerNavController = rememberNavController()

    val backStackEntry by innerNavController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination

    // Determine title based on current destination
    val topBarTitle = when {
        destination?.hasRoute<Routes.HomeScreen>() == true -> "NewsApp"
        destination?.hasRoute<Routes.FollowingScreen>() == true -> "Following"
        destination?.hasRoute<Routes.BookmarkScreen>() == true -> "Bookmark"
        else -> ""
    }

    Scaffold(
        topBar = {

            TopAppBar(
                title = topBarTitle,
                onSearchClick = {},
                onProfileClick = {}
            )
        },
        bottomBar = {
            BottomBar(innerNavController)
        },
        containerColor = Color(0xFFE5E5E5),
    ) { innerPadding ->

        NavHost(
            navController = innerNavController,
            startDestination = Routes.HomeScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Routes.HomeScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it }, // slide in from right
                        animationSpec = tween(1000)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(500)
                    )
                }
            ) {
                HomeScreen(
                    onSeeAll = {
                        navHostController.navigate(Routes.ALlTopHeadlineScreen)
                    },
                    onCardClick = {
                        navHostController.navigate(Routes.NewsDetailScreen)
                    })
            }

            composable<Routes.FollowingScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it }, // slide in from right
                        animationSpec = tween(1000)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(500)
                    )
                }
            ) {
                FollowingScreen(innerNavController)
            }

            composable<Routes.BookmarkScreen>(
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it }, // slide in from right
                        animationSpec = tween(1000)
                    )
                },
                exitTransition = {
                    slideOutHorizontally(
                        targetOffsetX = { -it },
                        animationSpec = tween(500)
                    )
                }
            ) {
                BookmarkScreen(innerNavController)
            }

        }

    }
}
