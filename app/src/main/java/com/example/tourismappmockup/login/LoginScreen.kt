package com.example.tourismappmockup.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tourismappmockup.R

@Composable
fun LoginScreen() {

    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginUiState = loginViewModel.loginUIState
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    ) {
        LoginScreenStructure(
            username = loginViewModel.usernameText.value,
            onUsernameChange = loginViewModel::onUsernameChange,
            password = loginViewModel.passwordText.value,
            onPasswordChange = loginViewModel::onPasswordChange
        )
    }
}

@Composable
fun LoginScreenStructure(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    LoginForm(
        username = username,
        onUsernameChange = onUsernameChange,
        password = password,
        onPasswordChange = onPasswordChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenTopBar() {
    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .heightIn(min = 56.dp),
        title = {
            Text(
                text = stringResource(R.string.label_login),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(vertical = 4.dp)
            )
        },
        navigationIcon = {},
        actions = {}
    )
}

@Composable
fun LoginForm(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit

) {

    LoginTitleLabel()
    LoginUsrnameInputText(
        username = username,
        onUsernameChange = onUsernameChange
    )
    LoginPasswordInputText(
        password = password,
        onPasswordChange = onPasswordChange
    )
    LoginButton(
        username = username,
        password = password
    )

    Login()

}

@Composable
fun LoginTitleLabel() {
    Text(
        text = stringResource(R.string.label_login),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp)
            .padding(vertical = 8.dp),
        style = TextStyle(
            fontSize = MaterialTheme.typography.titleLarge.fontSize
        )
    )
}

@Composable
fun LoginUsrnameInputText(
    username: String,
    onUsernameChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    CustomTextInputField(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .border(
                BorderStroke(1.dp, color = Color.LightGray),
                shape = RoundedCornerShape(10.dp)
            ),
        value = username,
        onValueChange = {
            onUsernameChange(it)
        },
        singleLine = true,
        placeholder = stringResource(R.string.placeholder_username),
        keyboardActions = KeyboardActions(
            onNext = {

                focusManager.moveFocus(FocusDirection.Next)
            }
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        visualTransformation = VisualTransformation.None
    )
    Spacer(
        modifier = Modifier
            .heightIn(min = 24.dp)
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPasswordInputText(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    var showPassword by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    CustomTextInputField(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                BorderStroke(1.dp, color = Color.LightGray),
                shape = RoundedCornerShape(10.dp)
            ),
        value = password,
        onValueChange = {
            onPasswordChange(it)
        },
        singleLine = true,
        placeholder = stringResource(R.string.placeholder_password),
        visualTransformation = if (showPassword) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            Icon(
                painter = if (showPassword) {
                    painterResource(id = R.drawable.password_visible)
                } else {
                    painterResource(id = R.drawable.password_hide)
                },
                contentDescription = null,
                modifier = Modifier.clickable {
                    showPassword = !showPassword
                }
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        )
    )
    Spacer(
        modifier = Modifier
            .heightIn(min = 24.dp)
    )
}

@Composable
fun Login() {

}

@Composable
fun LoginButton(
    username: String,
    password: String
) {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        enabled = if (username.isEmpty() || password.isEmpty() || password.length <= 6) false else true
    ) {
        Text(
            text = stringResource(R.string.label_submit)
        )
    }

}


@Composable
fun CustomTextInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean,
    placeholder: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        placeholder = {
            Text(text = placeholder)
        },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.surface
        )
    )
}


@Composable
fun ProgressBar() {

}

@Preview(showBackground = false)
@Composable
fun DefaultPrevieewLoginScreen() {

    Column {
        LoginScreen()
    }
}
