package com.example.newsapp.data.repositoryimpl

import com.example.newsapp.data.local.datastore.AuthDataStore
import com.example.newsapp.domain.repository.AuthDataStoreRepository
import kotlinx.coroutines.flow.Flow

class AuthDataStoreRepositoryImpl(
    private val dataStore: AuthDataStore
): AuthDataStoreRepository {

    override val isFirstTimeLogin: Flow<Boolean> = dataStore.isFirstTimeLogin

    override val isLoggedIn: Flow<Boolean> = dataStore.isLoggedIn

    override suspend fun setFirstTimeLogin(isFirstTime: Boolean) {
        dataStore.setFirstTimeLogin(isFirstTime)
    }

    override suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.setLoggedIn(isLoggedIn)
    }


}