package com.mayank.expensetracker.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mayank.expensetracker.R
import com.mayank.expensetracker.ui.theme.grid_x3

@Composable
internal fun DeleteTransactionScreen(onDeleteClick: () -> Unit) {
    Text(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = grid_x3, vertical = grid_x3)
        .clickable { onDeleteClick() },
        text = stringResource(R.string.delete_transaction),
        style = MaterialTheme.typography.body1.copy(
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Start
        )
    )
}