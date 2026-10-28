package com.bc.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    // Brand
    val primary: Color = Color(0xFF73513D),
    val secondary: Color = Color(0xFF958574),

    // Text
    val textPrimary: Color = Color(0xFF34291F),
    val textSecondary: Color = Color(0xFF958574),
    val textHint: Color = Color(0xFFA39484),

    // Backgrounds
    val background: Color = Color(0xFFF8F5EF),
    val surface: Color = Color(0xFFFFF8E9),
    val surfaceVariant: Color = Color(0xFFEFE8DE),
    val inputBackground: Color = Color(0xFFFFFCF8),
    val bottomBarBackground: Color = Color(0xFFFFFCF7),

    // Borders and accents
    val border: Color = Color(0xFFE8DED2),
    val outline: Color = Color(0xFFE8E0D6),
    val accent: Color = Color(0xFFE4D2C2),

    // On-color
    val onPrimary: Color = Color(0xFFFFF8E9),
    val onSecondary: Color = Color(0xFF34291F),
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors()
}