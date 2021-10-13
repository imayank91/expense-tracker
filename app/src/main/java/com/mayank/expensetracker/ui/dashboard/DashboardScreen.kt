package com.mayank.expensetracker.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mayank.expensetracker.internal.model.ExpenseData
import com.mayank.expensetracker.internal.model.Transaction
import com.mayank.expensetracker.internal.model.fakelist
import com.mayank.expensetracker.ui.components.ItemType
import com.mayank.expensetracker.ui.components.applyRoundedCornerShape
import com.mayank.expensetracker.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ExpenseScreen(vm: DashboardViewModel) {
    val viewState by vm.viewState.collectAsState()
    val bottomSheetScaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetState = bottomSheetScaffoldState,
        modifier = Modifier.wrapContentHeight(),
        sheetShape = RoundedCornerShape(topStart = grid_x3, topEnd = grid_x3),
        sheetContent = {
            AddTransactionBody(
                vm = vm,
                onCancel = {
                    scope.launch { bottomSheetScaffoldState.hide() }
                }, onSave = {
                    scope.launch { bottomSheetScaffoldState.hide() }
                    vm.onSaveTransaction()
                }
            )
        },
        content = {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                bottomSheetScaffoldState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        },
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "add",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                    }
                }
            ) {
                when (val state = viewState) {
                    is DashboardViewModel.DashboardScreenViewState.Success -> {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.screenState.transactions) { item ->
                                when (item) {
                                    is BalanceTransactionState -> ExpenseItem(item.description,
                                        item.amount, item.itemType, item.isCredit)
                                    is HeaderTransactionState -> ExpenseDateHeaderItem(date = item.title)
                                }
                            }


//                            item {
//                                ExpenseDateHeaderItem(date = "10th October, 2021")
//                                ExpenseItem(list = viewState.list)
//                            }
                        }
                    }
                }
            }
        })
}

@Composable
internal fun ExpenseDateHeaderItem(date: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(grid_x3, grid_x2, grid_x3, grid_x2),
        text = date,
        style = MaterialTheme.typography.body1.copy(
            color = MaterialTheme.colors.primary,
            fontWeight = FontWeight.SemiBold
        )
    )
}

@Composable
internal fun ExpenseItem(
    description: String,
    amount: String,
    itemType: ItemType,
    isCredit: Boolean,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = grid_x3)
        .applyRoundedCornerShape(itemType),
        horizontalArrangement = Arrangement.spacedBy(
            grid_x1)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(grid_x2),
            text = description,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Start
            )
        )
        Text(
            modifier = Modifier
                .padding(grid_x2),
            text = amount,
            style = MaterialTheme.typography.body2.copy(
                color = if (isCredit) StatusPositive else StatusError
            )
        )
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = grid_x3),
        color = Color.White,
        thickness = dividerWidth,
    )
}