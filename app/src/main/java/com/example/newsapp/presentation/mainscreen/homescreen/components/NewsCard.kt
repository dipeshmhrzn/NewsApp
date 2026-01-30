package com.example.newsapp.presentation.mainscreen.homescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.newsapp.ui.theme.InterDisplay

@Composable
fun NewsCard(
    onCardClick: () -> Unit,
    onShareClick:()->Unit,
    onBookmarkClick:()->Unit,
    urlToImage: String?,
    title: String,
    sourceName: String,
    publishedAt: String
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable {
                onCardClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = sourceName,
                        fontSize = 16.sp,
                        fontFamily = InterDisplay,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4E4B66)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = title,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 20.sp,
                        fontFamily = InterDisplay,
                        fontWeight = FontWeight.Normal,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(context).data(urlToImage).crossfade(true).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )

                        }
                    },
                    error = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFFF5F5F5)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No image",
                                fontFamily = InterDisplay,
                                fontSize = 12.sp
                            )
                        }

                    }
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = Color(0xFF4E4B66),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = publishedAt,
                    fontSize = 16.sp,
                    fontFamily = InterDisplay,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF4E4B66)
                )
                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(6.dp))
                        .clickable {
                            onShareClick()
                        }
                        .padding(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = Color(0xFF4E4B66),
                        modifier = Modifier.size(20.dp)

                    )
                }
                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(6.dp))
                        .clickable {
                            onBookmarkClick()
                        }
                        .padding(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
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
}
