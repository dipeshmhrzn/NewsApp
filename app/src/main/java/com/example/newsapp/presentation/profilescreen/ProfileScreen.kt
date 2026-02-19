package com.example.newsapp.presentation.profilescreen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.newsapp.R
import com.example.newsapp.domain.model.UserProfile
import com.example.newsapp.domain.util.Result
import com.example.newsapp.navigation.Routes
import com.example.newsapp.presentation.profilescreen.components.SignOutMenu
import com.example.newsapp.presentation.viewmodels.AuthViewModel
import com.example.newsapp.presentation.viewmodels.UserProfileViewModel
import com.example.newsapp.ui.theme.InterDisplay
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    userProfileViewModel: UserProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        userProfileViewModel.getUserProfile()
    }

    var isSignOutVisible by remember { mutableStateOf(false) }

    val userProfileState by userProfileViewModel.userProfile.collectAsState()

    val userProfile = (userProfileState as? Result.Success)?.data
    val email = userProfile?.emailAddress.orEmpty()
    val profilePicture = userProfile?.profilePicture.orEmpty()
    val isLoading = userProfileState is Result.Loading


    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF9F9F9)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFFF83758),
                    strokeWidth = 4.dp
                )
            }
        } else {
            Scaffold(
                containerColor = Color(0xFFFFFFFF)
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = email,
                            fontFamily = InterDisplay,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.align(Alignment.Center)
                        )

                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(32.dp)
                                .clip(CircleShape)
                                .clickable {
                                    navHostController.popBackStack()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color(0xFF737373)
                            )
                        }
                    }

                    if (profilePicture.isNotBlank()) {
                        AsyncImage(
                            model = profilePicture,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(80.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color(0xFFE5E5E5).copy(.3f)),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .clickable {
                                        navHostController.navigate(Routes.BookmarkScreen)
                                    }
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.BookmarkBorder,
                                    contentDescription = null,
                                    tint = Color(0xFF4E4B66),
                                    modifier = Modifier.size(25.dp)

                                )

                                Text(
                                    text = "Bookmark",
                                    fontSize = 18.sp,
                                    fontFamily = InterDisplay,
                                    color = Color(0xFF02040D),
                                    fontWeight = FontWeight.Normal
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(8.dp))
                                    .clickable {}
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Password,
                                    contentDescription = null,
                                    tint = Color(0xFF4E4B66),
                                    modifier = Modifier.size(25.dp)

                                )

                                Text(
                                    text = "Reset Password",
                                    fontSize = 18.sp,
                                    fontFamily = InterDisplay,
                                    color = Color(0xFF02040D),
                                    fontWeight = FontWeight.Normal
                                )
                            }
                        }

                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clip(RoundedCornerShape(30.dp))
                            .background(Color(0xFFE5E5E5).copy(.3f))
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    isSignOutVisible = !isSignOutVisible
                                }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = null,
                                tint = Color(0xFF4E4B66),
                                modifier = Modifier.size(25.dp)
                            )

                            Text(
                                text = "Log out",
                                fontSize = 18.sp,
                                fontFamily = InterDisplay,
                                color = Color(0xFF02040D),
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }

                }
            }
        }

        if (isSignOutVisible) {
            SignOutMenu(
                onDismiss = { isSignOutVisible = false },
                onYesClick = {
                    authViewModel.signOut()
                    navHostController.navigate(Routes.LoginScreen) {
                        popUpTo(0) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                    authViewModel.resetAuthState()
                    isSignOutVisible = false
                }
            )
        }


    }
}

@Preview
@Composable
private fun ProfilePrev() {
    NewsAppTheme {
        ProfileScreen(rememberNavController())
    }
}