package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.data.local.AuthDataStore
import com.example.newsapp.data.local.FollowDataStore
import com.example.newsapp.data.remote.NewsApiServices
import com.example.newsapp.data.repositoryimpl.AuthDataStoreRepositoryImpl
import com.example.newsapp.data.repositoryimpl.AuthRepositoryImpl
import com.example.newsapp.data.repositoryimpl.FollowRepositoryImpl
import com.example.newsapp.data.repositoryimpl.NewsRepositoryImpl
import com.example.newsapp.domain.repository.AuthDataStoreRepository
import com.example.newsapp.domain.repository.AuthRepository
import com.example.newsapp.domain.repository.FollowRepository
import com.example.newsapp.domain.repository.NewsRepository
import com.google.firebase.auth.FirebaseAuth
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
import io.ktor.http.ContentType.Application.Json
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




}