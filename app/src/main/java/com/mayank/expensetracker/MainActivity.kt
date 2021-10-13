package com.mayank.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.mayank.expensetracker.internal.di.AppComponent
import com.mayank.expensetracker.internal.navigation.AppNavHost
import com.mayank.expensetracker.ui.dashboard.ExpenseScreen
import com.mayank.expensetracker.ui.theme.ExpenseTrackerTheme
import javax.inject.Inject


class MainActivity : ComponentActivity() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory

    @ExperimentalUnitApi
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppComponent.initAndInject(this)
        setContent {
            ExpenseTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    ExpenseFlow(vmFactory)
                }
            }
        }
    }
}

@ExperimentalUnitApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun ExpenseFlow(vmFactory: ViewModelProvider.Factory) {
    val navController = rememberNavController()
    AppNavHost(navController = navController, vmFactory = vmFactory)
}
