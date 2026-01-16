package com.example.newsapp.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.PlayFairDisplay

@Composable
fun LoginScreen() {
    Scaffold(
        containerColor = Color(0xFFE5E5E5)
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

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Email*",
                fontSize = 16.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                lineHeight = 30.sp,
                color = Color(0xFF4E4B66),
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                singleLine = true,
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

            Text(
                text = "Password*",
                fontSize = 16.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                lineHeight = 30.sp,
                color = Color(0xFF4E4B66),
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordVisible = !passwordVisible
                        }
                    ) {
                        Icon(
                            painter = if (passwordVisible) painterResource(R.drawable.ic_visibilty_on) else painterResource(
                                R.drawable.ic_visibility_off
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                            tint = Color(0xFF4E4B66)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF0295F6),
                    unfocusedBorderColor = Color(0xFF4E4B66),
                    focusedTextColor = Color(0xFF050505),
                    unfocusedTextColor = Color(0xFF050505),
                    cursorColor = Color(0xFF0295F6)

                )
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

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0295F6),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp,
                    fontFamily = InterDisplay,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 30.sp,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "or continue with",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 18.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF4E4B66),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4E4B66).copy(alpha = .1f),
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )
                    Text(
                        text = "Google",
                        fontSize = 20.sp,
                        fontFamily = InterDisplay,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4E4B66)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "don’t have an account ? ",
                    fontSize = 18.sp,
                    fontFamily = InterDisplay,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF4E4B66),
                )
                TextButton(onClick = {},
                    contentPadding = PaddingValues(0.dp)) {
                    Text(
                        text = "Sign Up",
                        fontSize = 18.sp,
                        fontFamily = InterDisplay,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF0295F6),
                    )
                }
            }

        }


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    NewsAppTheme {
        LoginScreen()
    }
}