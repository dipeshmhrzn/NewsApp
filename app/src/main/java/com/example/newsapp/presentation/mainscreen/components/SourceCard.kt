package com.example.newsapp.presentation.mainscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.newsapp.navigation.Routes
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.PlayFairDisplay

@Composable
fun SourceCard() {

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .border(
                width = 1.dp,
                color = Color(0xFF737373),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {}
            .padding(10.dp)

    ) {
        Text(
            text = "ABC",
            fontFamily = InterDisplay,
            fontSize = 18.sp
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape)
                .border(
                    width = 1.dp,
                    color = Color(0xFF737373),
                    shape = CircleShape
                )
                .clickable {}
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.StarBorder,
                contentDescription = "Following",
                tint = Color(0xFF737373)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SourceCardPrev() {
    NewsAppTheme {
        SourceCard()
    }
}