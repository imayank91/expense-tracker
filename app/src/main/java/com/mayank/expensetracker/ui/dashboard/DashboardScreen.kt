package com.mayank.expensetracker.ui.dashboard

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.mayank.expensetracker.R
import com.mayank.expensetracker.internal.utils.toDollars
import com.mayank.expensetracker.ui.components.ItemType
import com.mayank.expensetracker.ui.components.applyRoundedCornerShape
import com.mayank.expensetracker.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
internal fun ExpenseScreen(vm: DashboardViewModel) {
    val viewState by vm.viewState.collectAsState()
    val bottomSheetScaffoldState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var showDeleteTransaction by remember { mutableStateOf(false) }
    var selectedTransaction by remember { mutableStateOf(-1) }
    ModalBottomSheetLayout(
        sheetState = bottomSheetScaffoldState,
        modifier = Modifier.wrapContentHeight(),
        sheetShape = RoundedCornerShape(topStart = grid_x3, topEnd = grid_x3),
        sheetContent = {
            when (showDeleteTransaction) {
                true -> DeleteTransactionScreen {
                    scope.launch { bottomSheetScaffoldState.hide() }
                    vm.deleteTransaction(selectedTransaction)
                }
                false -> AddTransactionBody(
                    vm = vm,
                    onCancel = {
                        scope.launch { bottomSheetScaffoldState.hide() }
                    }, onSave = {
                        scope.launch { bottomSheetScaffoldState.hide() }
                        vm.onSaveTransaction()
                    }
                )
            }
        },
        content = {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                showDeleteTransaction = false
                                delay(100)
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
                        Column(Modifier
                            .fillMaxSize()
                            .background(Blue05),
                            verticalArrangement = Arrangement.spacedBy(grid_x2)) {
                            with(state.screenState) {
                                SummaryView(balance, expense, income)
                            }
                            SlideInSlideOutVertically {
                                LazyColumn(modifier = Modifier
                                    .fillMaxSize()
                                    .applyRoundedCornerShape(ItemType.FIRST, Color.White)
                                    .animateContentSize()) {
                                    items(state.screenState.transactions) { item ->
                                        when (item) {
                                            is BalanceTransactionState -> ExpenseItem(item.description,
                                                item.amount, item.itemType, item.isCredit) {
                                                selectedTransaction = item.transactionId
                                                scope.launch {
                                                    showDeleteTransaction = true
                                                    delay(100)
                                                    bottomSheetScaffoldState.show()
                                                }
                                            }
                                            is HeaderTransactionState -> ExpenseDateHeaderItem(
                                                date = item.title)
                                        }
                                    }
                                    item { Spacer(modifier = Modifier.height(grid_x6)) }
                                }
                            }
                        }
                    }
                    is DashboardViewModel.DashboardScreenViewState.Empty -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(modifier = Modifier.align(Alignment.Center),
                                text = stringResource(R.string.no_transaction),
                                style = MaterialTheme.typography.h4.copy(
                                    color = Blue40,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
        })
}

@Composable
internal fun SummaryView(balance: Double, expense: Double, income: Double) {
    var progress by remember { mutableStateOf(0.0f) }
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    ).value
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(vertical = grid_x3), verticalArrangement = Arrangement.spacedBy(grid_x2)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TransactionView(modifier = Modifier.weight(1f),
                label = stringResource(R.string.expenses),
                value = expense.toDollars())
            TransactionView(modifier = Modifier.weight(1f),
                label = stringResource(R.string.income),
                value = income.toDollars())
            TransactionView(modifier = Modifier.weight(1f),
                label = stringResource(R.string.balance),
                value = balance.toDollars())
        }
        LinearProgressIndicator(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = grid_x3), progress = animatedProgress)
    }
    LaunchedEffect(balance) {
        delay(200)
        val ratio = (abs(expense / income * 100) / 100)
        if (ratio.isNaN()) return@LaunchedEffect
        progress = if (ratio <= 1) {
            ratio.toFloat()
        } else {
            1f
        }
    }
}

@Composable
internal fun TransactionView(modifier: Modifier, label: String, value: String) {
    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(grid_x0_5),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = MaterialTheme.typography.button.copy(
                color = MaterialTheme.colors.primary
            )
        )
        Text(text = value, style = MaterialTheme.typography.caption.copy(
            color = MaterialTheme.colors.primary
        ))
    }
}

@Composable
internal fun ExpenseDateHeaderItem(date: String) {
    Text(
        modifier = Modifier
            .padding(grid_x3, grid_x2, grid_x3, grid_x2),
        text = date,
        style = MaterialTheme.typography.body2.copy(
            color = Blue40,
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
    onClick: () -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = grid_x3)
        .applyRoundedCornerShape(itemType)
        .clickable { onClick() },
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