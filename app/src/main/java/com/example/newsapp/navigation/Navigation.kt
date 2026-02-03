package com.example.newsapp.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.domain.util.Result
import com.example.newsapp.presentation.profilescreen.ProfileScreen
import com.example.newsapp.presentation.searchscreen.SearchScreen
import com.example.newsapp.presentation.mainscreen.sourcescreen.SourcesDetailScreen
import com.example.newsapp.presentation.authscreen.LoginScreen
import com.example.newsapp.presentation.authscreen.SignUpScreen
import com.example.newsapp.presentation.mainscreen.homescreen.AllTopHeadlineScreen
import com.example.newsapp.presentation.mainscreen.MainScreen
import com.example.newsapp.presentation.viewmodels.NewsViewModel
import com.example.newsapp.presentation.onboardingscreen.OnBoardingScreen
import com.example.newsapp.presentation.splashscreen.SplashScreen
import com.example.newsapp.presentation.viewmodels.AuthDataStoreViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val newsViewModel: NewsViewModel = hiltViewModel()
    val newsState by newsViewModel.newsState.collectAsState()

    val authDataStoreViewModel: AuthDataStoreViewModel = hiltViewModel()
    val authDataStoreState by authDataStoreViewModel.authDataStoreState.collectAsState()


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
            SplashScreen(
                isLoading = authDataStoreState.isLoading,
                onFinish = {
                    val destination = when {
                        authDataStoreState.isFirstTimeLogin -> Routes.OnboardingScreen
                        authDataStoreState.isLoggedIn -> Routes.MainScreen
                        else -> Routes.LoginScreen
                    }
                    navController.navigate(destination) {
                        popUpTo(Routes.SplashScreen) {
                            inclusive = true
                        }
                    }
                }
            )
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
            OnBoardingScreen(
                navHostController = navController,
                authDataStoreViewModel = authDataStoreViewModel
            )
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
            LoginScreen(
                navHostController = navController,
                authDataStoreViewModel = authDataStoreViewModel
            )

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
            ProfileScreen(navHostController = navController)
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
                navHostController = navController,
                viewModel = newsViewModel
            )
        }


    }

}