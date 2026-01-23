package com.example.newsapp.presentation.mainscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun NewsCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "CNBC",
                    fontSize = 16.sp,
                    fontFamily = InterDisplay,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4E4B66)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Stocks rebound from big sell-off after Trump rules out military action on Greenland: Live updates - CNBC",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 20.sp,
                    fontFamily = InterDisplay,
                    fontWeight = FontWeight.Normal,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            AsyncImage(
                model = R.drawable.onboarding1,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                tint = Color(0xFF4E4B66),
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "4h ago",
                fontSize = 16.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF4E4B66)
            )
            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    tint = Color(0xFF4E4B66),
                    modifier = Modifier.size(20.dp)

                )
            }

            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                    contentDescription = null,
                    tint = Color(0xFF4E4B66),
                    modifier = Modifier.size(20.dp)

                )
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFF4E4B66),
        )
    }
}
