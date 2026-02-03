package com.example.newsapp.presentation.mainscreen.followingscreen.components

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.presentation.mainscreen.homescreen.components.NewsCard
import com.example.newsapp.presentation.utils.findActivity
import com.example.newsapp.presentation.utils.openWebsite
import com.example.newsapp.presentation.utils.shareUrlIntent
import com.example.newsapp.ui.theme.InterDisplay
import kotlin.Unit

@Composable
fun FollowingCard(
    isFollowed: Boolean,
    articles: List<Article>,
    onFollowClick: () -> Unit,
    onReadMoreClick: () -> Unit

) {
    if (articles.isEmpty()) return

    val context = LocalContext.current

    val shareLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)
    ) {
        Column {
            val sourceName = articles.first().source.name

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        onReadMoreClick()
                    }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = sourceName,
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

            articles.take(2).forEach { article ->
                NewsCard(
                    onCardClick = {
                        openWebsite(context, article.url)
                    },
                    urlToImage = article.urlToImage,
                    title = article.title,
                    sourceName = article.author ?: "",
                    publishedAt = article.publishedAt,
                    onShareClick = {
                        shareLauncher.launch(shareUrlIntent(article.url))
                    },
                    onBookmarkClick = {}
                )
            }
            Spacer(modifier = Modifier.height(10.dp))


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {
                        onReadMoreClick()
                    }

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFE5E5E5))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Read more from $sourceName",
                        fontFamily = InterDisplay,
                        fontSize = 18.sp,
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = null,
                        tint = Color(0xFF4E4B66)
                    )
                }
            }
        }
    }
}