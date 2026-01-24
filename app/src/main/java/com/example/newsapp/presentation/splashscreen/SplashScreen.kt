package com.example.newsapp.presentation.splashscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.Routes
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.PlayFairDisplay
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navHostController: NavHostController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFFFFF)
    ) {

        var startAnimation by remember { mutableStateOf(false) }

        val alpha by animateFloatAsState(
            targetValue = if (startAnimation) 1f else 0f,
            animationSpec = tween(durationMillis = 1200)
        )

        LaunchedEffect(Unit) {
            delay(300)
            startAnimation=true

            delay(1200) // animation duration
            navHostController.navigate(Routes.OnboardingScreen) {
                popUpTo(Routes.SplashScreen) { inclusive = true }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "NewsApp",
                fontFamily = PlayFairDisplay,
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.alpha(alpha)
            )
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    NewsAppTheme {
        SplashScreen(rememberNavController())
    }
}
