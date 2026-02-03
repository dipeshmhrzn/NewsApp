package com.example.newsapp.presentation.utils

import android.content.Intent


fun shareUrlIntent(url: String): Intent {
    return Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
        putExtra(Intent.EXTRA_TITLE, "Share via")
    }
}


