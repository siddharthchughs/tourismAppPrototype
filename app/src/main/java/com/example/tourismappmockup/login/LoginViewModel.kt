package com.example.tourismappmockup.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed interface LoginResult{
    data object Initial : LoginResult
    data object NotLoggedIn : LoginResult
    data object LoggedIn : LoginResult
    data object Failure : LoginResult

}

sealed interface LoginUIState{
    data object Loading : LoginUIState
    data object Loaded : LoginUIState
    data class TerminalError(
        val errorMessage:String
    ) : LoginUIState
}


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    val resultState = mutableStateOf(LoginResult.NotLoggedIn)
    val usernameText = mutableStateOf("")
    val passwordText = mutableStateOf("")

    val loginUIState : Flow<LoginUIState> = flow {

    }



    fun onUsernameChange(name:String){
        usernameText.value = name
    }

    fun onPasswordChange(password:String){
        passwordText.value = password
    }


}