package com.example.readersapp.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed interface LoginResult {
    data object Initial : LoginResult
    data object LoggedIn : LoginResult
    data object NotLoggedIn : LoginResult
    data class Failure(
        val error: String
    ) : LoginResult
}

sealed interface LoginUiState {
    data object Available : LoginUiState
    data object Loading : LoginUiState
    data object LoginLoaded : LoginUiState

    data class TerminalError(
        val error: String
    ) : LoginUiState
}

@HiltViewModel
class LoginViewModel @Inject constructor(
) : ViewModel() {

    private val uiLoginState = MutableStateFlow<LoginResult>(LoginResult.NotLoggedIn)
    private val auth: FirebaseAuth = Firebase.auth
    val usernameText = mutableStateOf("")
    val passwordText = mutableStateOf("")
    val name = mutableStateOf("")

    val loginUIState: Flow<LoginUiState> = uiLoginState.map {
        when (it) {
            is LoginResult.Failure -> LoginUiState.TerminalError("")
            LoginResult.Initial -> LoginUiState.Available
            LoginResult.LoggedIn -> LoginUiState.LoginLoaded
            LoginResult.NotLoggedIn -> LoginUiState.LoginLoaded
        }
    }

    fun onUsernameChange(username: String) {
        usernameText.value = username
    }

    fun onPasswordChange(password: String) {
        passwordText.value = password
    }

    fun login() =
        viewModelScope.launch {
                uiLoginState.value = LoginResult.Initial
                delay(1_000L)
                auth.signInWithEmailAndPassword(usernameText.value, passwordText.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            uiLoginState.value =LoginResult.LoggedIn
                            name.value = "${task.result.user?.email?.split('@')?.get(0)}"
                            Timber.i("Take me home :: ${task.result}}")
                        } else {
                            uiLoginState.value =LoginResult.Failure("")
                            Timber.i("Failed to connect ${task.result}")
                        }
                    }
        }

    fun loginErrorMessage(error: String) {
        uiLoginState.value = LoginResult.Failure(error = error)
    }

}