package com.example.readersapp.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.readersapp.R
import com.example.readersapp.navigation.ReaderRoute

@Composable
fun LoginScreen(
    navController: NavController
) {

    val loginRequestModel: LoginViewModel = hiltViewModel()
    val loginUIState =
        loginRequestModel.loginUIState.collectAsStateWithLifecycle(LoginUiState.Loading).value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        LoginStructure(
            loginUiState = loginUIState,
            username = loginRequestModel.usernameText.value,
            onUsernameChange = loginRequestModel::onUsernameChange,
            password = loginRequestModel.passwordText.value,
            onPasswordChange = loginRequestModel::onPasswordChange,
            errorMessage = loginRequestModel::loginErrorMessage,
            login = loginRequestModel::login,
            navController = navController
        )
    }
}


@Composable
fun LoginStructure(
    loginUiState: LoginUiState,
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    errorMessage: (String) -> Unit,
    login: () -> Unit,
    navController: NavController
) {
    when (loginUiState) {
        LoginUiState.Available, is LoginUiState.Loading -> {
            CreateUserProgressBar()
        }

        LoginUiState.LoginLoaded -> {
            LoginForm(
                username = username,
                onUsernameChange = onUsernameChange,
                password = password,
                onPasswordChange = onPasswordChange,
                errorMessage = errorMessage,
                login = login,
                navController = navController
            )

        }

        is LoginUiState.TerminalError -> {
            LoginTerminalError(
                errorMessage = errorMessage.toString() ?: ""
            )
        }

    }

}

@Composable
fun LoginForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    errorMessage: (String) -> Unit,
    login: () -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LoginLabelText(
            headerLabel = stringResource(R.string.login_label)
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        LoginUsernameInputField(
            username = username,
            onUsernameChange = {
                onUsernameChange(it)
            },
            label = stringResource(R.string.login_label_username),
            singleLine = true,
            maxLine = 1,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                color = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        LoginPasswordInput(
            password = password,
            onPasswordChange = {
                onPasswordChange(it)
            },
            passwordLabel = stringResource(R.string.login_label_password),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                color = MaterialTheme.colorScheme.primary
            ),
            login = login
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        LoginButton(
            username = username,
            password = password,
            login = login,
            navController = navController
        )

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        SignUpUser(navController = navController)
    }
}

@Composable
fun LoginLabelText(headerLabel: String) {
    Text(
        text = headerLabel,
        style = TextStyle(
            color = Color.Blue,
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )
    )
}


@Composable
fun LoginUsernameInputField(
    username: String,
    label: String,
    onUsernameChange: (String) -> Unit,
    onImeAction: () -> Unit = {},
    singleLine: Boolean,
    maxLine: Int = 1,
    modifier: Modifier = Modifier,
    textStyle: TextStyle
) {

    val focusManager = LocalFocusManager.current

    TextField(
        value = username,
        onValueChange = onUsernameChange,
        label = {
            Text(text = label)
        },
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(FocusDirection.Next)
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        singleLine = singleLine,
        maxLines = maxLine,
        modifier = modifier,
        textStyle = textStyle
    )
}


@Composable
fun LoginPasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordLabel: String,
    onImeAction: () -> Unit = {},
    trailingIcon: (() -> Unit)? = null,
    singleLine: Boolean,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    login:()->Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = password,
        onValueChange = onPasswordChange,
        label = {
            Text(text = passwordLabel)
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
            login()
        }),
        modifier = modifier,
        singleLine = singleLine,
        textStyle = textStyle
    )
}


@Composable
fun LoginButton(
    username: String,
    password: String,
    login: () -> Unit,
    navController: NavController
) {
    Button(
        onClick = {
            login()
            navController.navigate(ReaderRoute.ReaderHomeScreen.name){
                popUpTo(navController.graph.id){
                    inclusive = true
                }
            }
        },
        enabled = if (username.isEmpty() || password.isEmpty() || password.length <= 6) false else true
    ) {
        Text(
            text = stringResource(R.string.login_label)
        )
    }
}

@Composable
fun SignUpUser(navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = stringResource(R.string.label_new_user)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier
                .clickable {
                    navController.navigate(ReaderRoute.CreateAccountScreen.name)
                },
            text = stringResource(R.string.label_create_user)
        )

    }
}

@Composable
fun LoginTerminalError(
    errorMessage: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            style = TextStyle(
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CreateUserProgressBar() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
        CircularProgressIndicator(
            strokeWidth = 2.dp,
            trackColor = Color.Green
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )
    }
}


