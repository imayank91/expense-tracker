package com.mayank.expensetracker.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayank.expensetracker.internal.model.Transaction
import com.mayank.expensetracker.internal.usecase.AddTransactionUseCase
import com.mayank.expensetracker.internal.usecase.DeleteTransactionsUseCase
import com.mayank.expensetracker.internal.usecase.GetTransactionsUseCase
import com.mayank.expensetracker.internal.utils.InputValidator
import com.mayank.expensetracker.internal.utils.TransactionType
import com.mayank.expensetracker.internal.utils.formatDate
import com.mayank.expensetracker.internal.utils.parseDouble
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject


internal class DashboardViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val deleteTransactionsUseCase: DeleteTransactionsUseCase,
    private val transactionsUIStateMapper: TransactionsUIStateMapper,
) : ViewModel() {
    private val typeList = listOf(TransactionType.Expense.name, TransactionType.Income.name)
    private val _dashboardViewState: MutableStateFlow<DashboardScreenViewState> =
        MutableStateFlow(DashboardScreenViewState.Empty)
    val viewState: StateFlow<DashboardScreenViewState> = _dashboardViewState.asStateFlow()

    private val _addTransaction = MutableStateFlow(AddTransactionState(title = "",
        amount = "",
        type = "",
        date = LocalDate.now(),
        formattedDate = "",
        typeList = typeList,
        saveEnabled = false
    ))
    val addTransactionState: StateFlow<AddTransactionState> get() = _addTransaction.asStateFlow()

    private var title = MutableStateFlow("")
    private var amount = MutableStateFlow("")
    private var date = MutableStateFlow<LocalDate>(LocalDate.now())
    private var type = MutableStateFlow("")
    private var formattedDate = MutableStateFlow("")

    init {
        updateLatestTransaction()
        observeAddTransactionState()
    }

    private fun resetAddTransaction() {
        title.value = ""
        amount.value = ""
        type.value = ""
        date.value = LocalDate.now()
        formattedDate.value = ""
    }

    private fun observeAddTransactionState() {
        viewModelScope.launch {
            combine(title,
                amount,
                date,
                formattedDate,
                type) { title, amount, date, formattedDate, type ->
                AddTransactionState(
                    title = title,
                    amount = amount,
                    type = type,
                    date = date,
                    formattedDate = formattedDate,
                    typeList = typeList,
                    saveEnabled = InputValidator.isValid(title, amount, formattedDate, type))
            }.catch { e ->
                e.printStackTrace()
            }.collect {
                _addTransaction.value = it
            }
        }
    }

    private fun updateLatestTransaction() {
        viewModelScope.launch {
            getTransactionsUseCase.getTransactions().catch {

            }.collect { transaction ->
                when {
                    transaction.isNotEmpty() -> {
                        val (totalIncome, totalExpense) = transaction.partition { it.transactionType == TransactionType.Income.name }
                        val income = totalIncome.sumOf { it.amount }
                        val expense = totalExpense.sumOf { it.amount }
                        val balance = income - expense
                        _dashboardViewState.value =
                            DashboardScreenViewState.Success(DashboardScreenState(balance = balance,
                                income = income,
                                expense = expense,
                                transactions = transactionsUIStateMapper.map(
                                    transaction)))
                    }
                    transaction.isEmpty() -> {
                        _dashboardViewState.value = DashboardScreenViewState.Empty
                    }
                }
            }
        }
    }

    fun setTitle(input: String) {
        title.value = input
    }

    fun setAmount(input: String) {
        amount.value = input
    }

    fun setType(input: String) {
        type.value = input
    }

    fun setDate(input: LocalDate) {
        date.value = input
        formattedDate.value = input.formatDate()
    }

    fun onSaveTransaction() {
        with(addTransactionState.value) {
            val instant: Instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant()
            val timeInMillis = instant.toEpochMilli()
            val transaction = Transaction(title = title,
                amount = parseDouble(amount),
                transactionType = type,
                transactionDate = formattedDate,
                timestamp = timeInMillis
            )
            viewModelScope.launch {
                addTransactionUseCase.addTransaction(transaction)
                getTransactionsUseCase.getTransactions()
            }
        }
        resetAddTransaction()
    }

    fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            deleteTransactionsUseCase.deleteTransaction(id)
            getTransactionsUseCase.getTransactions()
        }
    }

    internal sealed class DashboardScreenViewState {
        class Success(val screenState: DashboardScreenState) : DashboardScreenViewState()
        class Error(val message: String) : DashboardScreenViewState()
        object Loading : DashboardScreenViewState()
        object Empty : DashboardScreenViewState()
    }

    internal class AddTransactionState(
        val title: String,
        val amount: String,
        val type: String,
        val date: LocalDate,
        val formattedDate: String,
        val typeList: List<String>,
        val saveEnabled: Boolean,
    )
}