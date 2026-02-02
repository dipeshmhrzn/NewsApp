package com.example.newsapp.presentation.authscreen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.ui.theme.InterDisplay

@Composable
fun EmailPasswordField(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    emailSupportingText: String? = null,
    passwordSupportingText: String? = null,
    isEmailError: Boolean = false,
    isPasswordError: Boolean = false,
) {

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
        onValueChange = onEmailChange,
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        isError = isEmailError,
        supportingText = emailSupportingText?.let {
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
            if (isEmailError) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp)
                )
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
        onValueChange = onPasswordChange,
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        isError = isPasswordError,
        supportingText = passwordSupportingText?.let {
            {
                Text(
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Red,
                    fontSize = 14.sp,
                    fontFamily = InterDisplay
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            if (isPasswordError) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                IconButton(
                    onClick = onPasswordVisibilityChange
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

}