package com.example.newsapp.presentation.mainscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.Routes
import com.example.newsapp.presentation.mainscreen.components.BottomBar
import com.example.newsapp.presentation.mainscreen.components.TopAppBar
import com.example.newsapp.ui.theme.InterDisplay

@Composable
fun MainScreen(
    navHostController: NavHostController,
    newsViewModel: NewsViewModel
) {

    val innerNavController = rememberNavController()

    val backStackEntry by innerNavController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination

    var isMenuVisible by remember { mutableStateOf(false) }


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
                        onMenuClick = {
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
                    FollowingScreen(innerNavController)
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
                        navHostController = navHostController,
                        viewModel = newsViewModel
                    )
                }

            }
        }
        if (isMenuVisible) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray.copy(alpha = 0.5f))
                        .clickable { isMenuVisible = false }
                )

                AnimatedVisibility(
                    visible = true,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(500)
                    ),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(500)
                    ),
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .background(Color(0xFFFFFFFF))
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {}
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .clickable {}
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.BookmarkBorder,
                                    contentDescription = null,
                                    tint = Color(0xFF4E4B66),
                                    modifier = Modifier.size(25.dp)

                                )

                                Text(
                                    text = "Save for later",
                                    fontSize = 20.sp,
                                    fontFamily = InterDisplay,
                                    color = Color(0xFF02040D),
                                    fontWeight = FontWeight.Normal
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .clickable {}
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null,
                                    tint = Color(0xFF4E4B66),
                                    modifier = Modifier.size(25.dp)

                                )

                                Text(
                                    text = "Share",
                                    fontSize = 20.sp,
                                    fontFamily = InterDisplay,
                                    color = Color(0xFF02040D),
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .clickable {}
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                    contentDescription = null,
                                    tint = Color(0xFF4E4B66),
                                    modifier = Modifier.size(25.dp)

                                )

                                Text(
                                    text = "Redirect to website",
                                    fontSize = 20.sp,
                                    fontFamily = InterDisplay,
                                    color = Color(0xFF02040D),
                                    fontWeight = FontWeight.Normal
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                        }
                    }
                }
            }
        }


    }
}
