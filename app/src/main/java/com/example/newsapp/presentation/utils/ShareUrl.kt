package com.example.newsapp.presentation.utils

import android.content.Context
import android.content.Intent
import android.os.SystemClock

private var lastShareClickTime = 0L
private const val SHARE_DEBOUNCE_MS = 800L

fun shareUrl(context: Context, url: String) {
    val now = SystemClock.elapsedRealtime()
    if (now - lastShareClickTime < SHARE_DEBOUNCE_MS) return
    lastShareClickTime = now

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, url)
    }

    val chooser = Intent.createChooser(intent, "Share via")
    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(chooser)
}