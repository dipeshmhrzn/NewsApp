package com.example.newsapp.domain.usecase.authdatastoreusecase

import com.example.newsapp.domain.repository.AuthDataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthDatastoreUseCase @Inject constructor(
    private val repository: AuthDataStoreRepository
) {

    fun isFirstTimeLogin(): Flow<Boolean> = repository.isFirstTimeLogin

    fun isLoggedIn(): Flow<Boolean> = repository.isLoggedIn
}