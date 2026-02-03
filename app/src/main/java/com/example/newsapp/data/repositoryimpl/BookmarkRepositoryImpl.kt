package com.example.newsapp.data.repositoryimpl

import com.example.newsapp.data.dto.topheadlines.Article
import com.example.newsapp.data.local.dao.BookmarkDao
import com.example.newsapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImpl(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {

    override fun getBookmarks(): Flow<List<Article>> {
        return bookmarkDao.getAllBookmarkedItems()
    }

    override suspend fun addBookmark(article: Article) {
        bookmarkDao.insertIntoBookmark(article)
    }

    override suspend fun removeBookmark(article: Article) {
        bookmarkDao.deleteFromBookmark(article)
    }

    override suspend fun isBookmarked(url: String): Boolean {
        return bookmarkDao.isItemBookmarked(url)
    }
}
