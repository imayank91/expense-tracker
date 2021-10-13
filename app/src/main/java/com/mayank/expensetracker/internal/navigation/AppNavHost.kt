package com.mayank.expensetracker.internal.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mayank.expensetracker.core.injectViewModel
import com.mayank.expensetracker.ui.dashboard.DashboardViewModel
import com.mayank.expensetracker.ui.dashboard.ExpenseScreen

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalUnitApi
@Composable
internal fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    vmFactory: ViewModelProvider.Factory,
    startDestination: String = AppRoutes.Dashboard,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppRoutes.Dashboard) {
            val vm: DashboardViewModel = vmFactory.injectViewModel()
            ExpenseScreen(vm)
        }
    }
}
