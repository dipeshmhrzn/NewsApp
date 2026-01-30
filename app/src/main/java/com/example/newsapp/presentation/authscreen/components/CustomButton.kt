package com.example.newsapp.presentation.authscreen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.ui.theme.InterDisplay

@Composable
fun CustomButton(
    onButtonClick: () -> Unit,
    buttonText: String
) {

    Button(
        onClick = onButtonClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF02040D),
            contentColor = Color.White
        )
    ) {
        Text(
            text = buttonText,
            fontSize = 20.sp,
            fontFamily = InterDisplay,
            fontWeight = FontWeight.Medium,
            lineHeight = 30.sp,
        )
    }
}