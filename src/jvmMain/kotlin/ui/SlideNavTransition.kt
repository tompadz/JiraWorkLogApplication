package ui

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.transition.NavTransition

@Composable
fun SlideNavTransition() = object : NavTransition {

    val scrollOffset = 600
    val scrollDuration = 300
    val fadeDuration = 100

    val enter: EnterTransition = slideInHorizontally(
        initialOffsetX = { +scrollOffset },
        animationSpec = tween(
            durationMillis = scrollDuration,
            easing = FastOutSlowInEasing
        )
    ) + fadeIn(animationSpec = tween(
        durationMillis = fadeDuration,
        easing = FastOutSlowInEasing
    ))

    val exit: ExitTransition = slideOutHorizontally(
        targetOffsetX = { -scrollOffset },
        animationSpec = tween(
            durationMillis = scrollDuration,
            easing = FastOutSlowInEasing
        )
    ) + fadeOut(animationSpec = tween(
        durationMillis = fadeDuration,
        easing = FastOutSlowInEasing
    ))

    override val createTransition: EnterTransition get() = enter
    override val destroyTransition: ExitTransition get() = exit
    override val pauseTransition: ExitTransition get() = exit
    override val resumeTransition: EnterTransition get() = enter
}