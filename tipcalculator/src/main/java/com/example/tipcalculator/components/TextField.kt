package com.example.tipcalculator.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.example.tipcalculator.R

@Composable
fun TotalAmountTextField(
    totalBillText: String,
    onAmountChange: (String) -> Unit,
    label: String,
    enabled: Boolean,
    isSingleLine: Boolean,
    maxLine: Int = 1,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions
) {
    OutlinedTextField(
        value = totalBillText,
        onValueChange = onAmountChange,
        label = { Text(text = label) },
        enabled = enabled,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.AttachMoney,
                contentDescription = stringResource(R.string.refresh)
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = isSingleLine,
        maxLines = maxLine,
        modifier = modifier,
        textStyle = textStyle
    )
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



