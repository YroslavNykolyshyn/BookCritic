package com.bc.core.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppMetrics(
    val emptyPadding: Dp = 0.dp,
    val dividerSize: Dp = 1.dp,
    val paddingTiny: Dp = 4.dp,
    val paddingTinyExtra: Dp = 2.dp,
    val paddingSmall: Dp = 8.dp,
    val paddingExtraSmall: Dp = 12.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingExtraMedium: Dp = 20.dp,
    val paddingBig: Dp = 24.dp,
    val paddingLarge: Dp = 32.dp,
    val paddingExtraLarge: Dp = 40.dp,

    // Icons
    val iconSizeTiny: Dp = 32.dp,
    val iconSizeExtraLarge: Dp = 128.dp,
)

internal val LocalDimensions = staticCompositionLocalOf { AppMetrics() }
