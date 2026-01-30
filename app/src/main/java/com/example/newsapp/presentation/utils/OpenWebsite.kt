package com.example.newsapp.presentation.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

fun openWebsite(context: Context, url: String) {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setShowTitle(true)
        .setInstantAppsEnabled(true)
        .build()

    try {
        customTabsIntent.launchUrl(context, url.toUri())
    } catch (e: ActivityNotFoundException) {
        try {
            val fallbackIntent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(fallbackIntent)
        } catch (ex: Exception) {
            Toast
                .makeText(context, "No browser found to open link", Toast.LENGTH_SHORT)
                .show()
        }
    }
}