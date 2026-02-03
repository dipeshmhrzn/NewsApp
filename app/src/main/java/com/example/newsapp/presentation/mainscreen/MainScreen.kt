package com.example.newsapp.presentation.mainscreen

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.navigation.Routes
import com.example.newsapp.presentation.mainscreen.components.BottomBar
import com.example.newsapp.presentation.mainscreen.components.MenuItems
import com.example.newsapp.presentation.mainscreen.components.TopAppBar
import com.example.newsapp.presentation.mainscreen.followingscreen.FollowingScreen
import com.example.newsapp.presentation.mainscreen.homescreen.HomeScreen
import com.example.newsapp.presentation.mainscreen.sourcescreen.SourceScreen
import com.example.newsapp.presentation.utils.findActivity
import com.example.newsapp.presentation.utils.openWebsite
import com.example.newsapp.presentation.utils.shareUrlIntent
import com.example.newsapp.presentation.viewmodels.NewsViewModel

@Composable
fun MainScreen(
    navHostController: NavHostController,
    newsViewModel: NewsViewModel
) {

    val innerNavController = rememberNavController()

    val backStackEntry by innerNavController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination

    var isMenuVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val shareLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    // Determine title based on current destination
    val topBarTitle = when {
        destination?.hasRoute<Routes.HomeScreen>() == true -> "NewsApp"
        destination?.hasRoute<Routes.FollowingScreen>() == true -> "Following"
        destination?.hasRoute<Routes.SourceScreen>() == true -> "Sources"
        else -> ""
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {

                TopAppBar(
                    title = topBarTitle,
                    onSearchClick = {
                        navHostController.navigate(Routes.SearchScreen)

                    },
                    onProfileClick = {
                        navHostController.navigate(Routes.ProfileScreen)
                    }
                )
            },
            bottomBar = {
                if (!isMenuVisible) {
                    BottomBar(innerNavController)
                }
            },
            containerColor = Color(0xFFFFFFFF),
        ) { innerPadding ->

            NavHost(
                navController = innerNavController,
                startDestination = Routes.HomeScreen,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable<Routes.HomeScreen>(
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearEasing
                            )
                        )
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        )
                    }
                ) {
                    HomeScreen(
                        onSeeAll = {
                            navHostController.navigate(Routes.ALlTopHeadlineScreen)
                        },
                        onMenuClick = { article ->
                            selectedArticle = article
                            isMenuVisible = !isMenuVisible
                        },
                        viewModel = newsViewModel
                    )
                }

                composable<Routes.FollowingScreen>(
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearEasing
                            )
                        )
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        )
                    }
                ) {
                    FollowingScreen(
                        onNavigateToSource = {
                            innerNavController.navigate(Routes.SourceScreen) {
                                popUpTo(Routes.HomeScreen) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        onReadMoreClick = { id ->
                            navHostController.navigate(
                                Routes.SourcesDetailScreen(
                                    sourceId = id
                                )
                            )
                        },
                        onNavigateToBookmark = {
                            navHostController.navigate(Routes.BookmarkScreen)
                        }
                    )
                }

                composable<Routes.SourceScreen>(
                    enterTransition = {
                        fadeIn(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = LinearEasing
                            )
                        )
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        )
                    }
                ) {
                    SourceScreen(
                        viewModel = newsViewModel,
                        onSourceClick = { id ->
                            navHostController.navigate(
                                Routes.SourcesDetailScreen(
                                    sourceId = id
                                )
                            )
                        }
                    )
                }

            }
        }
        if (isMenuVisible) {
            MenuItems(
                article = selectedArticle!!,
                onDismiss = {
                    isMenuVisible = false
                },
                onSaveClick = {},
                onShareClick = { article ->
                    shareLauncher.launch(shareUrlIntent(article.url))
                },
                onRedirectClick = { article ->
                    openWebsite(context, article.url)
                }
            )
        }


    }
}
