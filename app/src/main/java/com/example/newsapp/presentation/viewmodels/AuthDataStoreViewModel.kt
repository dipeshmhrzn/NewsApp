package com.example.newsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.model.AuthDataStoreState
import com.example.newsapp.domain.usecase.authdatastoreusecase.GetAuthDatastoreUseCase
import com.example.newsapp.domain.usecase.authdatastoreusecase.SetAuthDatastoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthDataStoreViewModel @Inject constructor(
    private val getAuthDatastoreUseCase: GetAuthDatastoreUseCase,
    private val setAuthDatastoreUseCase: SetAuthDatastoreUseCase
): ViewModel() {

    private val _authDataStoreState = MutableStateFlow(AuthDataStoreState())
    val authDataStoreState = _authDataStoreState.asStateFlow()

    init {
        observeAuthDataStore()
    }

    fun observeAuthDataStore(){
        viewModelScope.launch {
            combine(
                getAuthDatastoreUseCase.isFirstTimeLogin(),
                getAuthDatastoreUseCase.isLoggedIn()
            ){isFirstTimeLogin, isLoggedIn ->
                Log.d("DataStoreState", "DataStore values changed - isFirstTime: $isFirstTimeLogin, isLoggedIn: $isLoggedIn,, isLoading: ${_authDataStoreState.value.isLoading}")

                AuthDataStoreState(
                    isFirstTimeLogin = isFirstTimeLogin,
                    isLoggedIn = isLoggedIn,
                    isLoading = false
                )
            }.collect {newState->
                Log.d("DataStoreState", "Updating state - isFirstTime: ${newState.isFirstTimeLogin}, isLoggedIn: ${newState.isLoggedIn}, isLoading: ${newState.isLoading}")
                _authDataStoreState.value=newState
            }
        }
    }

    fun setFirstTimeLogin(isFirstTime: Boolean){
        viewModelScope.launch {
            runCatching {
                setAuthDatastoreUseCase.setFirstTimeLogin(isFirstTime)
            }.onFailure {error->
                Log.d("AuthDataStoreViewModel", "setFirstTimeLogin: $error")
            }
        }
    }

    fun setLoggedIn(isLoggedIn: Boolean){
        viewModelScope.launch {
            runCatching {
                setAuthDatastoreUseCase.setLoggedIn(isLoggedIn)
            }.onFailure {error->
                Log.d("AuthDataStoreViewModel", "setLoggedIn : $error")
            }
        }
    }

}