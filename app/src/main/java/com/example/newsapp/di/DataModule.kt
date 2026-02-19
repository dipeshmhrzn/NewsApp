package com.example.newsapp.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.room.Room
import com.example.newsapp.R
import com.example.newsapp.data.local.dao.BookmarkDao
import com.example.newsapp.data.local.database.NewsDatabase
import com.example.newsapp.data.local.datastore.AuthDataStore
import com.example.newsapp.data.local.datastore.FollowDataStore
import com.example.newsapp.data.remote.NewsApiServices
import com.example.newsapp.data.repositoryimpl.AuthDataStoreRepositoryImpl
import com.example.newsapp.data.repositoryimpl.AuthRepositoryImpl
import com.example.newsapp.data.repositoryimpl.BookmarkRepositoryImpl
import com.example.newsapp.data.repositoryimpl.FollowRepositoryImpl
import com.example.newsapp.data.repositoryimpl.NewsRepositoryImpl
import com.example.newsapp.data.repositoryimpl.UserProfileRepositoryImpl
import com.example.newsapp.domain.repository.AuthDataStoreRepository
import com.example.newsapp.domain.repository.AuthRepository
import com.example.newsapp.domain.repository.BookmarkRepository
import com.example.newsapp.domain.repository.FollowRepository
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.domain.repository.UserProfileRepository
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository{
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideNewsHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
                connectTimeoutMillis = 10000
                socketTimeoutMillis = 10000
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "newsapi.org"
                    encodedPath = "/v2/"
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideNewsApiServices(httpClient: HttpClient): NewsApiServices {
        return NewsApiServices(httpClient)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApiServices: NewsApiServices): NewsRepository {
        return NewsRepositoryImpl(newsApiServices)
    }

    @Provides
    @Singleton
    fun provideAuthDataStore(@ApplicationContext context: Context): AuthDataStore{
        return AuthDataStore(context)
    }

    @Provides
    @Singleton
    fun provideAuthDataStoreRepository(dataStore: AuthDataStore): AuthDataStoreRepository{
        return AuthDataStoreRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideFollowDataStore(@ApplicationContext context: Context): FollowDataStore{
        return FollowDataStore(context)
    }

    @Provides
    @Singleton
    fun provideFollowDataStoreRepository(dataStore: FollowDataStore): FollowRepository{
        return FollowRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase{
        return Room.databaseBuilder(
            context = context,
           klass =  NewsDatabase::class.java,
            name = "news_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(database: NewsDatabase): BookmarkDao{
        return database.BookmarkDao()
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(bookmarkDao: BookmarkDao): BookmarkRepository{
        return BookmarkRepositoryImpl(bookmarkDao)
    }

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager{
        return CredentialManager.create(context)
    }

    @Provides
    @Singleton
    fun provideGoogleIdOptions(
        @ApplicationContext context: Context
    ): GetGoogleIdOption{
        return GetGoogleIdOption.Builder()
            .setServerClientId(context.getString(R.string.web_client_id))
            .setAutoSelectEnabled(false)
            .setFilterByAuthorizedAccounts(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideCredentialRequest(
        getGoogleIdOption: GetGoogleIdOption
    ): GetCredentialRequest{
        return GetCredentialRequest.Builder()
            .addCredentialOption(getGoogleIdOption)
            .build()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideUserProfileRepository(firebaseFireStore: FirebaseFirestore): UserProfileRepository{
        return UserProfileRepositoryImpl(firebaseFireStore)
    }

}