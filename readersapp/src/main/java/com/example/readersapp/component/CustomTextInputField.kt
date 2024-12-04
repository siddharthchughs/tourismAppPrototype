package com.example.readersapp.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction

@Composable
fun TextInputField(
    searchText: String,
    onSearchUpdateChange: (String) -> Unit,
    placeholder:@Composable () -> Unit = {},
    onImeAction:@Composable () -> Unit = {},
    trailingIcon:@Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    singleLine: Boolean,
    modifier: Modifier = Modifier,
    textStyle: TextStyle
) {
    val keyboardController = LocalSoftwareKeyboardController.current


    TextField(
        value = searchText,
        onValueChange = onSearchUpdateChange,
        placeholder = placeholder,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        trailingIcon = trailingIcon,
        modifier = modifier,
        singleLine = singleLine,
        textStyle = textStyle
    )
}
