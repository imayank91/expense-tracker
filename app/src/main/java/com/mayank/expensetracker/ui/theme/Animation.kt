package com.mayank.expensetracker.ui.theme

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember


const val ANIMATION_DEFAULT_INITIAL_OFFSET_Y = 50
const val ANIMATION_DEFAULT_DURATION_MILLIS = 450

@ExperimentalAnimationApi
@Composable
fun SlideInSlideOutVertically(
    initialOffsetY: Int = ANIMATION_DEFAULT_INITIAL_OFFSET_Y,
    durationMillis: Int = ANIMATION_DEFAULT_DURATION_MILLIS,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visibleState = remember { MutableTransitionState(false) }
            .apply { targetState = true },
        enter = transitionSlideInVertically(initialOffsetY, durationMillis),
        exit = slideOutVertically(
            animationSpec = tween(
                durationMillis = durationMillis
            )
        ),
        content = { content() }
    )
}

@ExperimentalAnimationApi
fun transitionSlideInVertically(
    initialOffsetY: Int = ANIMATION_DEFAULT_INITIAL_OFFSET_Y,
    durationMillis: Int = ANIMATION_DEFAULT_DURATION_MILLIS,
): EnterTransition =
    slideInVertically(
        initialOffsetY = { initialOffsetY },
        animationSpec = tween(
            durationMillis = durationMillis
        )
    )
