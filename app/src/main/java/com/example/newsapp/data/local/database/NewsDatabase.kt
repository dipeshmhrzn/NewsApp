package com.example.newsapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.data.converter.SourceConverter
import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.data.local.dao.BookmarkDao

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun BookmarkDao(): BookmarkDao
}