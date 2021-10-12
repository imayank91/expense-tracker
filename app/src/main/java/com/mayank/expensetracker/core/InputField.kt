package com.mayank.expensetracker.core

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun ExpenseTrackerInputText(
    modifier: Modifier = Modifier,
    onTextChanged: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
    textStyle: TextStyle = MaterialTheme.typography.body2.copy(
        color = MaterialTheme.colors.primary,
        textAlign = TextAlign.Start
    ),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.None,
    focusState: Boolean = false,
    focusRequester: FocusRequester = remember { FocusRequester() },
    focusDirection: FocusDirection = FocusDirection.Down,
    onClick: (() -> Unit)? = null,
    maxChar: Int = -1,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    isError: Boolean = false,
    onFocusChanged: ((isFocused: Boolean) -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    TextField(
        value = textFieldValue,
        onValueChange = {
            if (maxChar != -1 && it.text.length <= maxChar)
                onTextChanged(it)
            else if (maxChar == -1)
                onTextChanged(it)
        },
        readOnly = readOnly,
        label = label,
        enabled = enabled,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick?.invoke() }
            .then(
                if (focusState) Modifier.focusRequester(focusRequester) else Modifier
            )
            .onFocusEvent { onFocusChanged?.invoke(it.isFocused) },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.moveFocus(focusDirection)
            },
            onDone = {
                keyboardController?.hide()
            }),
        maxLines = 1,
        singleLine = singleLine,
        textStyle = textStyle,
        colors = colors,
        isError = isError
    )
    DisposableEffect(Unit) {
        if (focusState) {
            focusRequester.requestFocus()
        }
        onDispose { }
    }
}

