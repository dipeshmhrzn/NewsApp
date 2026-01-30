package com.example.newsapp.presentation.utils

import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun getRelativeTime(publishedAt: String): String {
    return try {
        // Parse the API timestamp (UTC)
        val publishedDateTime =
            ZonedDateTime.parse(publishedAt, DateTimeFormatter.ISO_ZONED_DATE_TIME)

        // Get current time in UTC
        val now = ZonedDateTime.now(ZoneOffset.UTC)

        val years = ChronoUnit.YEARS.between(publishedDateTime, now)
        val months = ChronoUnit.MONTHS.between(publishedDateTime, now)
        val days = ChronoUnit.DAYS.between(publishedDateTime, now)
        val hours = ChronoUnit.HOURS.between(publishedDateTime, now)
        val minutes = ChronoUnit.MINUTES.between(publishedDateTime, now)

        // Determine relative time
        when {
            years > 0 -> "${years}y ago"
            months > 0 -> "${months}mo ago"
            days > 0 -> "${days}d ago"
            hours > 0 -> "${hours}h ago"
            minutes > 0 -> "${minutes}m ago"
            else -> "Just now"
        }
    } catch (e: Exception) {
        "" // fallback if parsing fails
    }
}
