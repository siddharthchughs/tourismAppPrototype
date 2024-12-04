package com.example.readersapp.createuser

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readersapp.setting.ApplicationSetting
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

sealed interface CreateUserUIState {
    data object Initial : CreateUserUIState
    data object Loaded : CreateUserUIState
    data object NotAvailable : CreateUserUIState
    data object Loading : CreateUserUIState
    data class Error(val error: String) : CreateUserUIState
}

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    val createUserRepository: CreateUserRepository
) : ViewModel() {

    val usernameText = mutableStateOf("")
    val passwordText = mutableStateOf("")
    var createUIFlow: Flow<CreateUserUIState> = flowOf(
        CreateUserUIState.Loaded
    )

    fun onUsernameChange(username: String) {
        usernameText.value = username
    }

    fun onPasswordChange(password: String) {
        passwordText.value = password
    }

    fun createUser() =
        viewModelScope.launch {
            createUserRepository.createUser(
                userName = usernameText.value,
                password = passwordText.value
            )
        }
}
