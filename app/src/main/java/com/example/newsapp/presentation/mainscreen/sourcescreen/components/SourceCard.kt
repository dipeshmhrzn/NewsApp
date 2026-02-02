package com.example.newsapp.presentation.mainscreen.sourcescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.ui.theme.InterDisplay

@Composable
fun SourceCard(
    source: String,
    isFollowed: Boolean,
    onSourceClick: () -> Unit,
    onFollowClick:()->Unit
) {

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
            .clickable {
                onSourceClick()
            }
            .padding(10.dp)

    ) {
        Text(
            text = source,
            fontFamily = InterDisplay,
            fontSize = 18.sp
        )

        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(shape = CircleShape)
                .border(
                    width = 1.dp,
                    color = if (isFollowed) Color.Black else Color(0xFF737373),
                    shape = CircleShape
                )
                .clickable {
                    onFollowClick()
                }
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isFollowed)
                    Icons.Filled.Star
                else
                    Icons.Outlined.StarBorder,
                contentDescription = "Following",
                tint = if (isFollowed) Color.Black else Color(0xFF737373)
            )
        }
    }
}
