package com.example.newsapp.presentation.mainscreen.homescreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun ShimmeredNewsCard(isLoading: Boolean = false) {

    if (isLoading) {
        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
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

                        Spacer(
                            modifier = Modifier
                                .width(150.dp)
                                .height(25.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .placeholder(
                                    true,
                                    highlight = PlaceholderHighlight.shimmer(
                                        highlightColor = Color(0xFF737373).copy(alpha = .1f)
                                    )
                                )
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .placeholder(
                                    true,
                                    highlight = PlaceholderHighlight.shimmer(
                                        highlightColor = Color(0xFF737373).copy(alpha = .1f)
                                    )
                                )
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Spacer(
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .placeholder(
                                true,
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = Color(0xFF737373).copy(alpha = .1f)
                                )
                            )
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .placeholder(
                                true,
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = Color(0xFF737373).copy(alpha = .1f)
                                )
                            )
                    )
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFF4E4B66),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun shimmerprev() {
    ShimmeredNewsCard(true)
}