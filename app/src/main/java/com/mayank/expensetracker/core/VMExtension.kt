package com.mayank.expensetracker.core

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
inline fun <reified T : ViewModel> ViewModelProvider.Factory.injectViewModel(
    key: String? = null,
    factory: ViewModelProvider.Factory? = this,
): T = viewModel(modelClass = T::class.java, key = key, factory = factory)