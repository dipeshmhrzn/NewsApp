package com.example.newsapp.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object SplashScreen: Routes()

    @Serializable
    data object OnboardingScreen: Routes()

    @Serializable
    data object LoginScreen: Routes()

    @Serializable
    data object SignUpScreen: Routes()


}