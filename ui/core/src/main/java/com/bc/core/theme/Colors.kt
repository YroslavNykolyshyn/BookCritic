package com.bc.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class AppColors(
    val brown: Color = Color(0xFF73513D),
    val textColor: Color = Color(0xFF34291F),
    val taupe: Color = Color(0xFF958574),
    val warmGray: Color = Color(0xFFE8E0D6),
    val cream: Color = Color(0xFFEFE8DE),
    val ivory: Color = Color(0xFFF8F5EF),
    val beige: Color = Color(0xFFE4D2C2),
    val parchment: Color = Color(0xFFFFF8E9),
    val border: Color = Color(0xFFE8DED2),
    val fieldBackground: Color = Color(0xFFFFFCF8),
    val textTint: Color = Color(0xFFA39484),
)

val LocalAppColors = staticCompositionLocalOf {
    AppColors()
}