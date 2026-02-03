package com.example.newsapp.presentation.onboardingscreen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.PlayFairDisplay
import kotlinx.coroutines.launch

@Composable
fun OnboardingPager(
    items: List<OnboardingItem>,
    onNavigateToLoginScreen: () -> Unit
) {

    val pagerState = rememberPagerState { items.size }
    val scope = rememberCoroutineScope()

    val isLastPage = pagerState.currentPage == items.size - 1

    val skipWeight by animateFloatAsState(
        targetValue = if (isLastPage) 0f else 1f,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        ),
        label = "SkipWeight"
    )

    val nextWeight by animateFloatAsState(
        targetValue = if (isLastPage) 2f else 1f,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        ),
        label = "NextWeight"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
            ) { page ->
                val isCurrentPage = pagerState.currentPage == page
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = items[page].image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(500.dp)
                            .fillMaxWidth()
                            .alpha(if (isCurrentPage) 1f else 0f)

                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = items[page].text1,
                        fontFamily = PlayFairDisplay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 38.sp,
                        textAlign = TextAlign.Start,
                        lineHeight = 45.sp,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .alpha(if (isCurrentPage) 1f else 0f)

                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = items[page].text2,
                        fontFamily = InterDisplay,
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Start,
                        lineHeight = 25.sp,
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp)
                            .alpha(if (isCurrentPage) 1f else 0f)

                    )
                    Spacer(modifier = Modifier.height(28.dp))

                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(items.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    val width by animateDpAsState(
                        targetValue = if (isSelected) 24.dp else 12.dp,
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        ),
                    )
                    val color by animateColorAsState(
                        targetValue = if (isSelected) Color(0xFF02040D) else Color.Gray.copy(
                            alpha = 0.5f
                        ),
                        animationSpec = tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing
                        ),
                    )

                    Box(
                        modifier = Modifier
                            .padding(1.dp)
                            .height(8.dp)
                            .width(width)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color)
                    )
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (skipWeight > 0f) {
                Button(
                    onClick = {
                        onNavigateToLoginScreen()
                    },
                    modifier = Modifier
                        .weight(skipWeight)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    border = BorderStroke(1.dp, Color.Black)
                ) {
                    Text(
                        text = "Skip For Now",
                        fontFamily = InterDisplay,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }
            }
            Button(
                onClick = {
                    scope.launch {
                        if (!isLastPage) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            onNavigateToLoginScreen()
                        }
                    }
                },
                modifier = Modifier
                    .weight(nextWeight)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF02040D),
                    contentColor = Color.White
                )

            ) {
                Text(
                    text = if (isLastPage) "Get Started " else "Next",
                    fontFamily = InterDisplay,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = Modifier
                )
            }
        }
    }
}

