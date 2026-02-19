package com.example.newsapp.presentation.viewmodels

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.usecase.authdatastoreusecase.SetAuthDatastoreUseCase
import com.example.newsapp.domain.usecase.authusecase.GoogleSignInUseCase
import com.example.newsapp.domain.usecase.authusecase.LoginUseCase
import com.example.newsapp.domain.usecase.authusecase.SignOutUseCase
import com.example.newsapp.domain.usecase.authusecase.SignupUseCase
import com.example.newsapp.domain.util.Result
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.FirebaseUser
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
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val setAuthDatastoreUseCase: SetAuthDatastoreUseCase,
    private val credentialManager: CredentialManager,
    private val getCredentialRequest: GetCredentialRequest
) : ViewModel() {

    private val _authState = MutableStateFlow<Result<String>>(Result.Idle)
    val authState = _authState.asStateFlow()

    private val _googleAuthState = MutableStateFlow<Result<FirebaseUser>>(Result.Idle)
    val googleAuthState = _googleAuthState.asStateFlow()

    private val _openAddGoogleAccountEvent = MutableStateFlow(false)
    val openAddGoogleAccountEvent = _openAddGoogleAccountEvent.asStateFlow()

    fun signUp(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = Result.Loading
            delay(300)
            val result = signupUseCase(email, password)
            Log.d("AuthViewModel", "signUp: $result")
            _authState.value = result
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = Result.Loading
            delay(300)
            val result = loginUseCase(email, password)
            if (result is Result.Success) {
                setAuthDatastoreUseCase.setLoggedIn(true)
            }
            Log.d("AuthViewModel", "login: $result")
            _authState.value = result
        }
    }

    fun signInWithGoogle(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val credentialResponse = credentialManager.getCredential(context,getCredentialRequest)
                if (credentialResponse.credential is CustomCredential && credentialResponse.credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL){
                    val googleSignInToken = GoogleIdTokenCredential.createFrom(credentialResponse.credential.data)
                    val idToken = googleSignInToken.idToken
                    val result =googleSignInUseCase(idToken)
                    _googleAuthState.value=result
                    if (result is Result.Success){
                        setAuthDatastoreUseCase.setLoggedIn(true)
                        setAuthDatastoreUseCase.setFirstTimeLogin(false)
                    }
                }
            }catch (e: GetCredentialCancellationException){

            }catch (e: NoCredentialException){

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) {
            _authState.value = Result.Loading
            delay(300)
            val result = signOutUseCase()
            Log.d("AuthViewModel", "signOut: $result")

            if (result is Result.Success) {
                setAuthDatastoreUseCase.setLoggedIn(false)
            }
            _authState.value = result
        }
    }

    fun resetAddGoogleAccountEvent() {
        _openAddGoogleAccountEvent.value = false
    }

    fun getAddGoogleAccountIntent(): Intent {
        return Intent(Settings.ACTION_ADD_ACCOUNT).apply {
            putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
        }
    }

    fun resetAuthState() {
        _authState.value = Result.Idle
    }
}