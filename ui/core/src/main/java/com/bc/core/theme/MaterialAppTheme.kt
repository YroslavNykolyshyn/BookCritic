package com.bc.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Brand palette
private val Brown = Color(0xFF73513D)
private val Espresso = Color(0xFF34291F)
private val Taupe = Color(0xFF958574)
private val WarmGray = Color(0xFFE8E0D6)
private val Cream = Color(0xFFEFE8DE)
private val Ivory = Color(0xFFF8F5EF)
private val Beige = Color(0xFFE4D2C2)
private val Parchment = Color(0xFFFFF8E9)

private val LightColorScheme = lightColorScheme(
    primary = Brown,
    onPrimary = Color.White,
    primaryContainer = Beige,
    onPrimaryContainer = Espresso,

    secondary = Taupe,
    onSecondary = Color.White,
    secondaryContainer = Cream,
    onSecondaryContainer = Espresso,

    tertiary = Taupe,
    onTertiary = Color.White,
    tertiaryContainer = Parchment,
    onTertiaryContainer = Espresso,

    background = Ivory,
    onBackground = Espresso,

    surface = Parchment,
    onSurface = Espresso,
    surfaceVariant = WarmGray,
    onSurfaceVariant = Espresso,

    outline = Taupe,
    outlineVariant = WarmGray,

    inverseSurface = Espresso,
    inverseOnSurface = Ivory,
    inversePrimary = Beige,

    surfaceTint = Brown
)

private val DarkColorScheme = darkColorScheme(
    primary = Beige,
    onPrimary = Espresso,
    primaryContainer = Brown,
    onPrimaryContainer = Parchment,

    secondary = Beige,
    onSecondary = Espresso,
    secondaryContainer = Taupe,
    onSecondaryContainer = Parchment,

    tertiary = WarmGray,
    onTertiary = Espresso,
    tertiaryContainer = Brown,
    onTertiaryContainer = Parchment,

    background = Espresso,
    onBackground = Ivory,

    surface = Color(0xFF40352D),
    onSurface = Ivory,
    surfaceVariant = Brown,
    onSurfaceVariant = WarmGray,

    outline = Taupe,
    outlineVariant = Brown,

    inverseSurface = Ivory,
    inverseOnSurface = Espresso,
    inversePrimary = Brown,

    surfaceTint = Beige
)

@Composable
fun MaterialAppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography(),
        content = content
    )
}