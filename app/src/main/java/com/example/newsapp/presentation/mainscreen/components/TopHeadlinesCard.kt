package com.example.newsapp.presentation.mainscreen.components

import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun TopHeadLinesCard() {

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardWidth = screenWidth * 0.9f

    Column(
        modifier = Modifier
            .width(cardWidth)
            .padding(start = 16.dp, top = 16.dp, end = 8.dp)
    ) {

        AsyncImage(
            model = R.drawable.onboarding1,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sean Conlon, Chloe Taylor, Pia Singh",
            fontSize = 18.sp,
            fontFamily = InterDisplay,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF4E4B66)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Stocks rebound from big sell-off after Trump rules out military action on Greenland: Live updates - CNBC",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 20.sp,
            fontFamily = InterDisplay,
            fontWeight = FontWeight.Normal,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "CNBC",
                fontSize = 16.sp,
                fontFamily = InterDisplay,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF737373)

            )

            Spacer(modifier = Modifier.width(10.dp))
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

            ShareHandler(url = "https://google.com") { onShare ->
                IconButton(onClick = onShare) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = Color(0xFF4E4B66),
                        modifier = Modifier.size(20.dp)

                    )
                }
            }

            OpenWebsiteHandler(url = "https://google.com") { onRedirect ->
                IconButton(onClick = onRedirect) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = null,
                        tint = Color(0xFF4E4B66),
                        modifier = Modifier.size(20.dp)

                    )
                }
            }

        }


    }
}

@Composable
fun ShareHandler(
    url: String,
    content: @Composable (onShare: () -> Unit) -> Unit
) {
    val context = LocalContext.current

    val shareLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { }

    content {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }

        shareLauncher.launch(
            Intent.createChooser(intent, "Share via")
        )
    }
}

@Composable
fun OpenWebsiteHandler(
    url: String,
    content: @Composable (onRedirect: () -> Unit) -> Unit
) {

    val context= LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }

    content{
        val builder = CustomTabsIntent.Builder().apply {
            setShowTitle(true)
            setInstantAppsEnabled(true)
        }
        val customTabsIntent = builder.build()
        try {
            customTabsIntent.launchUrl(context, url.toUri())
        } catch (e: ActivityNotFoundException) {
            try {
                val fallbackIntent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(fallbackIntent)
            } catch (ex: Exception) {
                Toast.makeText(context, "No browser found to open link", Toast.LENGTH_SHORT).show()
            }
        }
    }
}