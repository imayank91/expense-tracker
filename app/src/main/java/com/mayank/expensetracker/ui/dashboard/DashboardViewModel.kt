package com.mayank.expensetracker.ui.dashboard

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mayank.expensetracker.internal.model.Transaction
import com.mayank.expensetracker.internal.utils.InputValidator
import com.mayank.expensetracker.internal.utils.formatDate
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

internal class DashboardViewModel @Inject constructor() : ViewModel() {
    private val typeList = listOf("Expense", "Income")
    private val _addTransaction =
        MutableStateFlow(AddTransactionState(title = "",
            amount = "",
            type = "",
            date = LocalDate.now(),
            formattedDate = "",
            typeList = typeList,
            saveEnabled = false)
        )
    val addTransactionState: StateFlow<AddTransactionState> get() = _addTransaction.asStateFlow()

    private var title = MutableStateFlow("")
    private var amount = MutableStateFlow("")
    private var date = MutableStateFlow<LocalDate>(LocalDate.now())
    private var type = MutableStateFlow("")
    private var formattedDate = MutableStateFlow("")

    init {
        observeAddTransactionState()
    }

    private fun observeAddTransactionState() {
        viewModelScope.launch {
            combine(title,
                amount,
                date,
                formattedDate,
                type) { title, amount, date, formattedDate, type ->
                AddTransactionState(title = title,
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

    fun onSaveTransaction(){

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