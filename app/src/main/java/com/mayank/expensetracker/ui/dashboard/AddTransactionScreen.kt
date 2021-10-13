package com.mayank.expensetracker.ui.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.mayank.expensetracker.R
import com.mayank.expensetracker.core.ExpenseTrackerInputText
import com.mayank.expensetracker.internal.utils.formatDate
import com.mayank.expensetracker.ui.components.ExpenseTrackerPrimaryButton
import com.mayank.expensetracker.ui.components.ExpenseTrackerSecondaryButton
import com.mayank.expensetracker.ui.theme.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
internal fun AddTransactionBody(vm: DashboardViewModel, onCancel: () -> Unit, onSave: () -> Unit) {
    val transaction by vm.addTransactionState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val transactionList = transaction.typeList
    var titleValue by remember {
        mutableStateOf(TextFieldValue(transaction.title,
            TextRange(transaction.title.length)))
    }
    var amountValue by remember {
        mutableStateOf(TextFieldValue(transaction.amount,
            TextRange(transaction.amount.length)))
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(grid_x3, grid_x2, grid_x3, grid_x2),
        verticalArrangement = Arrangement.spacedBy(
            grid_x1),
        horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Text(
                text = stringResource(R.string.add_transaction),
                style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.primary)
            )
        }
        item {
            Box(modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart)) {
                ExpenseTrackerInputText(
                    textFieldValue = TextFieldValue(transaction.type),
                    enabled = false,
                    onTextChanged = {},
                    onClick = {
                        expanded = true
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.transaction_type),
                            style = MaterialTheme.typography.body2.copy(color = Blue80)
                        )
                    },
                    focusState = false,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White)
                ) {
                    transactionList.forEach { s ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            vm.setType(s)
                        }) {
                            Text(
                                text = s,
                                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.primary)
                            )
                        }
                    }
                }
            }
        }
        val (focusRequester) = FocusRequester.createRefs()
        item {
            ExpenseTrackerInputText(
                textFieldValue = titleValue,
                onTextChanged = {
                    titleValue = it
                    vm.setTitle(it.text)
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.title),
                        style = MaterialTheme.typography.body2.copy(color = Blue80)
                    )
                },
                focusRequester = focusRequester,
                focusState = false,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
            )
        }
        item {
            ExpenseTrackerInputText(
                textFieldValue = amountValue,
                onTextChanged = {
                    amountValue = if (it.text.isEmpty()){
                        it
                    } else {
                        when (it.text.toDoubleOrNull()) {
                            null -> amountValue
                            else -> it
                        }
                    }
                    vm.setAmount(it.text)
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.amount),
                        style = MaterialTheme.typography.body2.copy(color = Blue80)
                    )
                },
                leadingIcon = {
                    Text(
                        text = stringResource(R.string.dollar),
                        style = MaterialTheme.typography.h5.copy(color = Blue80)
                    )
                },
                focusRequester = focusRequester,
                focusState = false,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
            )
        }
        item {
            val dialogState = rememberMaterialDialogState()
            MaterialDialog(
                dialogState = dialogState,
                buttons = {
                    positiveButton(stringResource(R.string.ok))
                    negativeButton(stringResource(R.string.cancel))
                }
            ) {
                datepicker(initialDate = transaction.date) { date ->
                    vm.setDate(date)
                }
            }
            ExpenseTrackerInputText(
                textFieldValue = TextFieldValue(transaction.formattedDate),
                onTextChanged = {},
                enabled = false,
                placeholder = {
                    Text(
                        text = stringResource(R.string.choose_date),
                        style = MaterialTheme.typography.body2.copy(color = Blue80)
                    )
                },
                onClick = {
                    dialogState.show()
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = "date",
                        modifier = Modifier.size(ButtonDefaults.IconSize),
                        tint = MaterialTheme.colors.primary
                    )
                },
                focusRequester = focusRequester,
                focusState = false,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
            )
        }
        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(top = grid_x4, bottom = grid_x1),
                horizontalArrangement = Arrangement.spacedBy(grid_x1_5)) {
                ExpenseTrackerSecondaryButton(modifier = Modifier.weight(1f),
                    buttonText = stringResource(R.string.cancel), onButtonClick = onCancel)
                ExpenseTrackerPrimaryButton(
                    modifier = Modifier.weight(1f),
                    enabled = transaction.saveEnabled,
                    buttonText = stringResource(R.string.save),
                    onButtonClick = {
                        amountValue = TextFieldValue()
                        titleValue = TextFieldValue()
                        onSave()
                    }
                )
            }
        }
    }
}



