package com.mayank.expensetracker.internal.navigation

object AppRoutes {
    const val Dashboard = "Dashboard"
    const val AddTransaction = "AddTransaction"
}

internal sealed class AppRoute(var id: String) {
    object Dashboard : AppRoute(AppRoutes.Dashboard)
    object AddTransaction : AppRoute(AppRoutes.AddTransaction)
}