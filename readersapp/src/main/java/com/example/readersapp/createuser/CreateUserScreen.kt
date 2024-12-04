package com.example.readersapp.createuser

import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.readersapp.R
import com.example.readersapp.login.LoginButton
import com.example.readersapp.login.LoginLabelText
import com.example.readersapp.login.LoginPasswordInput
import com.example.readersapp.login.LoginStructure
import com.example.readersapp.login.LoginUiState
import com.example.readersapp.login.LoginUsernameInputField
import com.example.readersapp.login.LoginViewModel
import com.example.readersapp.login.SignUpUser
import com.example.readersapp.navigation.ReaderRoute
import javax.annotation.meta.When

@Composable
fun CreateUserScreen(
    navController: NavController,
    pressUp: () -> Unit
) {
    val createUserViewModel: CreateUserViewModel = hiltViewModel()
    val createUserUIState =
        createUserViewModel.createUIFlow.collectAsState(CreateUserUIState.Loading).value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .background(Color.White)
    ) {
        CreateUserTopAppBar(
            pressUp = pressUp
        )
        CreateUserScreenStructure(
            createUserUIState = createUserUIState,
            username = createUserViewModel.usernameText.value,
            onUsernameChange = createUserViewModel::onUsernameChange,
            password = createUserViewModel.passwordText.value,
            onPasswordChange = createUserViewModel::onPasswordChange,
            createUser = createUserViewModel::createUser,
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUserTopAppBar(
    pressUp: () -> Unit
) {
    TopAppBar(
        title = {
            Text(stringResource(R.string.label_create_user))
        },
        navigationIcon = {
            IconButton(onClick = {
                pressUp()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
        }

    )
}


@Composable
fun CreateUserScreenStructure(
    createUserUIState: CreateUserUIState,
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    createUser: () -> Unit,
    navController: NavController
) {
    when (createUserUIState) {
        CreateUserUIState.Initial, CreateUserUIState.Loading -> {
            CreateUserProgressBar()
        }

        CreateUserUIState.Loaded -> {
            CreateUserForm(
                username = username,
                onUsernameChange = onUsernameChange,
                password = password,
                onPasswordChange = onPasswordChange,
                createUser = createUser,
                navController = navController
            )
        }

        CreateUserUIState.NotAvailable,is CreateUserUIState.Error -> {}
    }
}

@Composable
fun CreateUserForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    createUser: () -> Unit,
    navController: NavController

) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        CreateUsernameInputField(
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

        CreatePasswordInputField(
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
            )
        )
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        CreateUserButton(
            username = username,
            password = password,
            createUser = createUser,
            navController = navController
        )
    }
}

@Composable
fun CreateUserHeaderText(headerLabel: String) {
    Text(
        text = headerLabel,
        style = TextStyle(
            color = Color.Blue,
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )
    )
}

@Composable
fun CreateUsernameInputField(
    username: String,
    label: String,
    onUsernameChange: (String) -> Unit,
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
fun CreatePasswordInputField(
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordLabel: String,
    onImeAction: () -> Unit = {},
    trailingIcon: (() -> Unit)? = null,
    singleLine: Boolean,
    modifier: Modifier = Modifier,
    textStyle: TextStyle
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
        }),
        modifier = modifier,
        singleLine = singleLine,
        textStyle = textStyle
    )
}

@Composable
fun CreateUserButton(
    username: String,
    password: String,
    createUser: () -> Unit,
    navController: NavController
) {
    Button(
        onClick = {
            createUser()
            navController.navigate(ReaderRoute.LoginScreen.name) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        },
        enabled = if (username.isEmpty() || password.isEmpty() || password.length <= 6) false else true
    ) {
        Text(
            text = stringResource(R.string.label_submit)
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

@Composable
fun TerminalError(error: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            text = error,
            style = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Medium
            )
        )
    }
}

