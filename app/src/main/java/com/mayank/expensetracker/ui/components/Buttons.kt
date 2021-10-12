package com.mayank.expensetracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.mayank.expensetracker.ui.theme.Blue10
import com.mayank.expensetracker.ui.theme.Secondary3
import com.mayank.expensetracker.ui.theme.button_large
import com.mayank.expensetracker.ui.theme.dividerWidth

@Composable
fun ExpenseTrackerSecondaryButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .height(button_large)
            .fillMaxWidth()
            .clip(CircleShape)
            .background(color = Color.White)
            .border(
                width = dividerWidth,
                color = MaterialTheme.colors.primary,
                shape = CircleShape
            ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Secondary3),
        onClick = onButtonClick
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.primary)
        )
    }
}

@Composable
fun ExpenseTrackerPrimaryButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonText: String,
    onButtonClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .height(button_large)
            .fillMaxWidth()
            .clip(CircleShape),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
            disabledBackgroundColor = Blue10
        ),
        enabled = enabled,
        onClick = onButtonClick
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.button.copy(color = Secondary3)
        )
    }
}