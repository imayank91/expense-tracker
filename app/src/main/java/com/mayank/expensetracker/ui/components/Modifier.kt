package com.mayank.expensetracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mayank.expensetracker.ui.theme.Blue05
import com.mayank.expensetracker.ui.theme.corner_xs

fun Modifier.applyRoundedCornerShape(
    itemType: ItemType,
    backgroundColor: Color = Blue05,
): Modifier {
    val shape: RoundedCornerShape
    val cornerRadius = corner_xs
    val noCornerRadius = 0.dp
    shape = when (itemType) {
        ItemType.ONLY -> RoundedCornerShape(cornerRadius, cornerRadius, cornerRadius, cornerRadius)
        ItemType.FIRST -> RoundedCornerShape(
            cornerRadius, cornerRadius, noCornerRadius, noCornerRadius
        )
        ItemType.LAST -> RoundedCornerShape(
            noCornerRadius, noCornerRadius, cornerRadius, cornerRadius
        )
        else -> RoundedCornerShape(noCornerRadius, noCornerRadius, noCornerRadius, noCornerRadius)
    }

    return this.background(color = backgroundColor, shape = shape)
}

enum class ItemType {
    FIRST, LAST, ONLY, DEFAULT;

    companion object {
        fun parse(current: Int, size: Int): ItemType = when {
            size == 1 -> ONLY
            current == 0 -> FIRST
            current == size - 1 -> LAST
            else -> DEFAULT
        }
    }
}