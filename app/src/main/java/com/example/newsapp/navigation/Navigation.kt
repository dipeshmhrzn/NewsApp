package com.example.newsapp.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.domain.util.Result
import com.example.newsapp.presentation.ProfileScreen
import com.example.newsapp.presentation.SearchScreen
import com.example.newsapp.presentation.SourcesDetailScreen
import com.example.newsapp.presentation.auth.LoginScreen
import com.example.newsapp.presentation.auth.SignUpScreen
import com.example.newsapp.presentation.mainscreen.AllTopHeadlineScreen
import com.example.newsapp.presentation.mainscreen.MainScreen
import com.example.newsapp.presentation.mainscreen.NewsDetailScreen
import com.example.newsapp.presentation.mainscreen.NewsViewModel
import com.example.newsapp.presentation.onboardingscreen.OnBoardingScreen
import com.example.newsapp.presentation.splashscreen.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val newsViewModel: NewsViewModel = hiltViewModel()
    val newsState by newsViewModel.newsState.collectAsState()

    NavHost(navController = navController, startDestination = Routes.SplashScreen) {
        composable<Routes.SplashScreen>(

            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = FastOutSlowInEasing
                    )

                )
            }
        ) {
            SplashScreen(navHostController = navController)
        }

        composable<Routes.OnboardingScreen>(
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
            OnBoardingScreen(navHostController = navController)
        }

        composable<Routes.LoginScreen>(
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
            LoginScreen(navHostController = navController)

        }

        composable<Routes.SignUpScreen>(
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
            SignUpScreen(navHostController = navController)

        }

        composable<Routes.MainScreen>(
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
            MainScreen(
                navHostController = navController,
                newsViewModel = newsViewModel
            )
        }


        composable<Routes.ALlTopHeadlineScreen>(
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

            val articles = (newsState as? Result.Success)?.data ?: emptyList()

            AllTopHeadlineScreen(
                navHostController = navController,
                articles = articles
            )
        }

        composable<Routes.NewsDetailScreen>(
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
            NewsDetailScreen(navController)
        }


        composable<Routes.ProfileScreen>(
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
            ProfileScreen()
        }

        composable<Routes.SearchScreen>(
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
            SearchScreen()
        }

        composable<Routes.SourcesDetailScreen>(
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
        ) { backStackEntry ->
            val sourceId = backStackEntry.arguments?.getString("sourceId") ?: ""
            SourcesDetailScreen(
                sourceId = sourceId,
                viewModel = newsViewModel
            )
        }


    }

}