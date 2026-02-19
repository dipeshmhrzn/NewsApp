package com.example.newsapp.presentation.authscreen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsapp.domain.util.Result
import com.example.newsapp.domain.util.ValidationErrors
import com.example.newsapp.presentation.authscreen.components.ButtonState
import com.example.newsapp.presentation.authscreen.components.CustomButton
import com.example.newsapp.presentation.viewmodels.AuthViewModel
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.PlayFairDisplay

@Composable
fun ForgotPasswordScreen(
    prefilledEmail: String?,
    navHostController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }

    val context = LocalContext.current

    val authState by authViewModel.authState.collectAsState()

    var emailError by remember { mutableStateOf<String?>(null) }

    val buttonState = when (authState) {
        is Result.Loading -> ButtonState.LOADING
        is Result.Success -> ButtonState.SUCCESS
        else -> ButtonState.IDLE
    }

    LaunchedEffect(authState) {
        when (authState) {

            is Result.Error -> {
                val error = (authState as Result.Error).message
                emailError = when (error) {
                    is ValidationErrors.EmailError -> {
                        error.message
                    }

                    is String -> {
                        error
                    }

                    else -> {
                        null
                    }
                }
            }

            Result.Idle, Result.Loading, is Result.Success -> {
                emailError = null
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
                text = "Forgot",
                fontSize = 50.sp,
                fontFamily = PlayFairDisplay,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Password?",
                fontSize = 50.sp,
                fontFamily = PlayFairDisplay,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0295F6)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "We will send you a message \nto set or reset your new password",
                fontSize = 24.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                lineHeight = 30.sp,
                color = Color(0xFF4E4B66)
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = " Email*",
                fontSize = 16.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                lineHeight = 30.sp,
                color = Color(0xFF4E4B66),
            )

            OutlinedTextField(
                value = prefilledEmail ?: email,
                onValueChange = {
                    if (prefilledEmail == null) {
                        email = it
                        emailError = null
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                isError = emailError != null,
                enabled = prefilledEmail == null,
                supportingText = emailError?.let {
                    {
                        Text(
                            text = it,
                            color = Color.Red,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            fontFamily = InterDisplay
                        )
                    }
                },
                trailingIcon = {
                    when {
                        emailError != null -> {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Error",
                                tint = Color.Red,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        prefilledEmail != null -> {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Read-only",
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF0295F6),
                    unfocusedBorderColor = Color(0xFF4E4B66),
                    focusedTextColor = Color(0xFF050505),
                    unfocusedTextColor = Color(0xFF050505),
                    cursorColor = Color(0xFF0295F6)

                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                onButtonClick = {
                    focusManager.clearFocus()
                    val emailToSend = prefilledEmail ?: email
                    authViewModel.resetPassword(emailToSend)
                },
                buttonText = "Send Link",
                buttonState = buttonState,
                onSuccessAnimationFinished = {
                    navHostController.popBackStack()
                    Toast.makeText(
                        context,
                        "Password reset link sent to your email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }


    }
}