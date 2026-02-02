package com.example.newsapp.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecase.authdatastoreusecase.SetAuthDatastoreUseCase
import com.example.newsapp.domain.usecase.authusecase.LoginUseCase
import com.example.newsapp.domain.usecase.authusecase.SignOutUseCase
import com.example.newsapp.domain.usecase.authusecase.SignupUseCase
import com.example.newsapp.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signupUseCase: SignupUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val setAuthDatastoreUseCase: SetAuthDatastoreUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<Result<String>>(Result.Idle)
    val authState = _authState.asStateFlow()

    fun signUp(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = Result.Loading
            delay(300)
            val result = signupUseCase(email, password)
            Log.d("AuthViewModel", "signUp: $result")
            _authState.value = result
        }
    }

    fun login(email: String,password: String){
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value= Result.Loading
            delay(300)
            val result=loginUseCase(email,password)
            if (result is Result.Success) {
                    setAuthDatastoreUseCase.setLoggedIn(true)
            }
            Log.d("AuthViewModel", "login: $result")
            _authState.value=result
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value= Result.Loading
            delay(300)
            val result = signOutUseCase()
            Log.d("AuthViewModel", "signOut: $result")
            _authState.value = result
        }
    }

    fun resetAuthState() {
        _authState.value = Result.Idle
    }
}