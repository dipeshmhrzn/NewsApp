package com.example.newsapp.presentation.onboardingscreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.newsapp.R
import com.example.newsapp.presentation.onboardingscreen.components.OnboardingItem
import com.example.newsapp.presentation.onboardingscreen.components.OnboardingPager

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier) {
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

    OnboardingPager(items = items)

}