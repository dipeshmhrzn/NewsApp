package com.example.newsapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object SplashScreen : Routes()

    @Serializable
    data object OnboardingScreen : Routes()

    @Serializable
    data object LoginScreen : Routes()

    @Serializable
    data object SignUpScreen : Routes()

    @Serializable
    data object MainScreen : Routes()

    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object FollowingScreen : Routes()

    @Serializable
    data object BookmarkScreen : Routes()

    @Serializable
    data object ALlTopHeadlineScreen : Routes()

    @Serializable
    data object NewsDetailScreen : Routes()

}