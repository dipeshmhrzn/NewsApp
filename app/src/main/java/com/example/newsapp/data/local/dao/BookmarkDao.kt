package com.example.newsapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.dto.topheadlines.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM articles")
    fun getAllBookmarkedItems(): Flow<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoBookmark(article: Article)

    @Delete
    suspend fun deleteFromBookmark(article: Article)

    @Query("SELECT EXISTS(SELECT 1 FROM articles WHERE url = :url)")
    suspend fun isItemBookmarked(url: String): Boolean
}

