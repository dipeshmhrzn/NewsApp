package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.newsapp.navigation.Navigation
import com.example.newsapp.presentation.auth.LoginScreen
import com.example.newsapp.presentation.onboardingscreen.OnBoardingScreen
import com.example.newsapp.presentation.onboardingscreen.components.OnboardingPager
import com.example.newsapp.presentation.splashscreen.SplashScreen
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Navigation()
            }
        }
    }
}