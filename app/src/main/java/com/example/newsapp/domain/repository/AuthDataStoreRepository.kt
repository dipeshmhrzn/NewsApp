package com.example.newsapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthDataStoreRepository {

    val isFirstTimeLogin : Flow<Boolean>

    val isLoggedIn:Flow<Boolean>

    suspend fun setFirstTimeLogin(isFirstTime:Boolean)

    suspend fun setLoggedIn(isLoggedIn:Boolean)

}