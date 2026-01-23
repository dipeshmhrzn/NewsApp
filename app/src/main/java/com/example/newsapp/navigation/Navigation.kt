package com.example.newsapp.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.presentation.auth.LoginScreen
import com.example.newsapp.presentation.auth.SignUpScreen
import com.example.newsapp.presentation.mainscreen.AllTopHeadlineScreen
import com.example.newsapp.presentation.mainscreen.MainScreen
import com.example.newsapp.presentation.mainscreen.NewsDetailScreen
import com.example.newsapp.presentation.onboardingscreen.OnBoardingScreen
import com.example.newsapp.presentation.splashscreen.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SplashScreen) {
        composable<Routes.SplashScreen>(

            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(500)
                )
            }
        ) {
            SplashScreen(navHostController = navController)
        }

        composable<Routes.OnboardingScreen>(
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
            OnBoardingScreen(navHostController = navController)
        }

        composable<Routes.LoginScreen>(
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
            LoginScreen(navHostController = navController)

        }

        composable<Routes.SignUpScreen>(
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
            SignUpScreen(navHostController = navController)

        }

        composable<Routes.MainScreen>(
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
            MainScreen(navController)
        }


        composable<Routes.ALlTopHeadlineScreen>(
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
            AllTopHeadlineScreen(navController)
        }

        composable<Routes.NewsDetailScreen>(
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
            NewsDetailScreen(navController)
        }

    }

}