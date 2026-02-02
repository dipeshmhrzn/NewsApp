package com.example.newsapp.presentation.onboardingscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.navigation.Routes
import com.example.newsapp.presentation.onboardingscreen.components.OnboardingItem
import com.example.newsapp.presentation.onboardingscreen.components.OnboardingPager
import com.example.newsapp.presentation.viewmodels.AuthDataStoreViewModel

@Composable
fun OnBoardingScreen(
    navHostController: NavHostController,
    authDataStoreViewModel: AuthDataStoreViewModel
) {
    val items = listOf(
        OnboardingItem(
            image = R.drawable.onboarding1,
            text1 = "Stay Informed, Anytime, Anywhere",
            text2 = "Welcome to our news app, your go-to source for breaking news, exclusive content and personalized content",
        ),
        OnboardingItem(
            image = R.drawable.onboarding2,
            text1 = "Be a Knowledgeable Global Citizen",
            text2 = "Unlock a personalized news experience that matches your interests and preferences. Your news, your way!",
        ),
        OnboardingItem(
            image = R.drawable.onboarding3,
            text1 = "Elevate Your News Experience Now!",
            text2 = "Join out vibrant community of news enthusiasts. Share your thoughts, and engage in meaningful discussions.",
        )
    )

    OnboardingPager(
        items = items,
        onNavigateToLoginScreen = {
            authDataStoreViewModel.setFirstTimeLogin(false)

            navHostController.navigate(Routes.LoginScreen) {
                popUpTo(Routes.OnboardingScreen) {
                    inclusive = true
                }
            }
        }
    )
}