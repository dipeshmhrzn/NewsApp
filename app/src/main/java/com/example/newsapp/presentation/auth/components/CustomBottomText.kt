package com.example.newsapp.presentation.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.ui.theme.InterDisplay

@Composable
fun CustomBottomText(text1: String, text2: String, onTextClick: () -> Unit) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = text1,
            fontSize = 18.sp,
            fontFamily = InterDisplay,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF4E4B66),
        )
        TextButton(
            onClick = onTextClick,
            contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = text2,
                fontSize = 18.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF0295F6),
            )
        }
    }
}