package com.example.newsapp.presentation.authscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.domain.util.Result
import com.example.newsapp.domain.util.ValidationErrors
import com.example.newsapp.navigation.Routes
import com.example.newsapp.presentation.authscreen.components.ButtonState
import com.example.newsapp.presentation.authscreen.components.CustomBottomText
import com.example.newsapp.presentation.authscreen.components.CustomButton
import com.example.newsapp.presentation.authscreen.components.EmailPasswordField
import com.example.newsapp.presentation.authscreen.components.SocialSignInOptions
import com.example.newsapp.presentation.viewmodels.AuthViewModel
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.PlayFairDisplay

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val authState by authViewModel.authState.collectAsState()

    val buttonState = when (authState) {
        is Result.Loading -> ButtonState.LOADING
        is Result.Success -> ButtonState.SUCCESS
        else -> ButtonState.IDLE
    }

    LaunchedEffect(Unit) {
        authViewModel.resetAuthState()
    }

    LaunchedEffect(authState) {
        when (authState) {

            is Result.Error -> {
                val error = (authState as Result.Error).message
                when (error) {
                    is ValidationErrors.EmailError -> {
                        emailError = error.message
                        passwordError = null
                    }

                    is ValidationErrors.PasswordError -> {
                        emailError = null
                        passwordError = error.message
                    }

                    is String -> {
                        emailError = null
                        passwordError = error
                    }

                    else -> {
                        emailError = null
                        passwordError = null
                    }
                }

            }

            Result.Idle, Result.Loading ,is Result.Success-> {
                emailError = null
                passwordError = null
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFFFFFFFF)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Hello",
                fontSize = 50.sp,
                fontFamily = PlayFairDisplay,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Again!",
                fontSize = 50.sp,
                fontFamily = PlayFairDisplay,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0295F6)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Welcome back you’ve\nbeen missed",
                fontSize = 24.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                lineHeight = 30.sp,
                color = Color(0xFF4E4B66)
            )

            Spacer(modifier = Modifier.height(28.dp))

            EmailPasswordField(
                email = email,
                onEmailChange = {
                    email = it
                    emailError = null
                },
                password = password,
                onPasswordChange = {
                    password = it
                    passwordError = null
                },
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                isEmailError = emailError != null,
                isPasswordError = passwordError != null,
                emailSupportingText = emailError,
                passwordSupportingText = passwordError
            )

            Spacer(modifier = Modifier.height(6.dp))

            TextButton(onClick = {}) {
                Text(
                    text = "Forgot the password ?",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 16.sp,
                    fontFamily = InterDisplay,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 30.sp,
                    color = Color(0xFF0295F6),
                    textAlign = TextAlign.End
                )
            }

            CustomButton(
                onButtonClick = {
                    authViewModel.login(email, password)
                },
                buttonText = "Login",
                buttonState = buttonState,
                onSuccessAnimationFinished = {
                    navHostController.navigate(Routes.MainScreen) {
                        popUpTo(Routes.LoginScreen) {
                            inclusive = true
                        }
                        launchSingleTop=true
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            SocialSignInOptions(
                onGoogleSignIn = {}
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomBottomText(
                text1 = "Don’t have an account? ",
                text2 = "Sign Up",
                onTextClick = {
                    navHostController.navigate(Routes.SignUpScreen)
                }
            )

        }


    }
}
