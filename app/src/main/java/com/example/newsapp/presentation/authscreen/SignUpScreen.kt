package com.example.newsapp.presentation.authscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.navigation.Routes
import com.example.newsapp.presentation.authscreen.components.CustomBottomText
import com.example.newsapp.presentation.authscreen.components.CustomButton
import com.example.newsapp.presentation.authscreen.components.EmailPasswordField
import com.example.newsapp.presentation.authscreen.components.SocialSignInOptions
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.PlayFairDisplay

@Composable
fun SignUpScreen(navHostController: NavHostController) {
    Scaffold(
        containerColor = Color(0xFFFFFFFF)
    ) { innerPadding ->

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Hello!",
                fontSize = 50.sp,
                fontFamily = PlayFairDisplay,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0295F6)

            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Signup to get Started",
                fontSize = 24.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                lineHeight = 30.sp,
                color = Color(0xFF4E4B66)
            )

            Spacer(modifier = Modifier.height(28.dp))

            EmailPasswordField(
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                onButtonClick = {},
                buttonText = "Sign Up"
            )

            Spacer(modifier = Modifier.height(24.dp))

            SocialSignInOptions(
                onGoogleSignIn = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomBottomText(
                text1 = "Already have an account ? ",
                text2 = "Login",
                onTextClick = {
                    navHostController.navigate(Routes.LoginScreen) {
                        popUpTo(Routes.SignUpScreen) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpScreenPreview() {

    NewsAppTheme {
        SignUpScreen(rememberNavController())
    }
}