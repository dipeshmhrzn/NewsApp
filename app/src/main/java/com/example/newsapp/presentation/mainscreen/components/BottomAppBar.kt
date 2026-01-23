package com.example.newsapp.presentation.mainscreen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsapp.navigation.Routes

@Composable
fun BottomBar(navHostController: NavHostController) {

    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val destination = backStackEntry?.destination

    NavigationBar(
        containerColor = Color(0xFF737373).copy(alpha = .1f)
    ) {


        NavigationBarItem(
            selected = destination?.hasRoute<Routes.HomeScreen>() == true,
            onClick = {
                navHostController.navigate(Routes.HomeScreen) {
                    popUpTo(Routes.HomeScreen) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = if (destination?.hasRoute<Routes.HomeScreen>() == true ) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = "Home"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = Color(0xFF02040D)
            )

        )

        NavigationBarItem(
            selected = destination?.hasRoute<Routes.FollowingScreen>() == true,
            onClick = {
                navHostController.navigate(Routes.FollowingScreen) {
                    popUpTo(Routes.HomeScreen) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = if (destination?.hasRoute<Routes.FollowingScreen>() == true ) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    contentDescription = "Following"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = Color(0xFF02040D)
            )
        )

        NavigationBarItem(
            selected = destination?.hasRoute<Routes.BookmarkScreen>() == true,
            onClick = {
                navHostController.navigate(Routes.BookmarkScreen) {
                    popUpTo(Routes.HomeScreen) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                Icon(
                    imageVector = if (destination?.hasRoute<Routes.BookmarkScreen>() == true ) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Bookmark"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                indicatorColor = Color(0xFF02040D)
            )
        )
    }

}