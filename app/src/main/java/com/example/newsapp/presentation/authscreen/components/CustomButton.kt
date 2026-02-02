package com.example.newsapp.presentation.authscreen.components

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.ui.theme.InterDisplay
import kotlinx.coroutines.delay

enum class ButtonState {
    IDLE,
    LOADING,
    SUCCESS
}

@Composable
fun CustomButton(
    onButtonClick: () -> Unit,
    buttonText: String,
    buttonState: ButtonState = ButtonState.IDLE,
    onSuccessAnimationFinished: (() -> Unit)? = null
) {

    val backgroundColor = when (buttonState) {
        ButtonState.SUCCESS -> Color(0xFF2E7D32)
        else -> Color(0xFF02040D)
    }

    var buttonWidth by remember { mutableIntStateOf(0) }

    val tickOffset by animateFloatAsState(
        targetValue = if (buttonState == ButtonState.SUCCESS) 0f else 1f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )

    Button(
        onClick = onButtonClick,
        enabled = buttonState != ButtonState.LOADING,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .onGloballyPositioned { coordinates ->
                buttonWidth = coordinates.size.width
            },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.White,
            disabledContainerColor = backgroundColor
        )
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            if (buttonState != ButtonState.SUCCESS) {
                Text(
                    text = if (buttonState == ButtonState.LOADING) "Loading..." else buttonText,
                    fontSize = 20.sp,
                    fontFamily = InterDisplay,
                    color = Color.White
                )
            }

            if (buttonState == ButtonState.LOADING) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterEnd)
                        .offset(x = (-12).dp)
                )
            }

            if (buttonState == ButtonState.SUCCESS) {
                LaunchedEffect(Unit) {
                    delay(500)
                    onSuccessAnimationFinished?.invoke()
                }

                Icon(
                    imageVector = Icons.Default.TaskAlt,
                    contentDescription = "Success",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                        .offset(x = (buttonWidth / 2f * tickOffset).dp),
                    tint = Color.White
                )
            }
        }
    }
}


