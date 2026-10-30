package com.bc.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private fun AppColors.toColorScheme() = lightColorScheme(
    primary = primary,
    onPrimary = surface,

    secondary = secondary,
    onSecondary = textPrimary,

    background = background,
    onBackground = textPrimary,

    surface = surface,
    onSurface = textPrimary,

    surfaceVariant = surfaceVariant,
    onSurfaceVariant = textPrimary,

    outline = outline,
)

object AppTheme {

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val dimensions: AppMetrics
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current
}

@Composable
fun MaterialAppTheme(
    content: @Composable () -> Unit,
) {
    val colors = AppColors()
    val dimensions = AppMetrics()

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalDimensions provides dimensions,
    ) {
        MaterialTheme(
            colorScheme = colors.toColorScheme(),
            typography = MaterialTheme.typography,
            content = content,
        )
    }
}