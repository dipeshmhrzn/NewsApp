package com.example.newsapp.domain.usecase.authdatastoreusecase

import com.example.newsapp.domain.repository.AuthDataStoreRepository
import javax.inject.Inject

class SetAuthDatastoreUseCase @Inject constructor(
    private val repository: AuthDataStoreRepository
) {
    suspend fun setFirstTimeLogin(isFirstTime: Boolean) {
        repository.setFirstTimeLogin(isFirstTime)
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        repository.setLoggedIn(isLoggedIn)
    }
}