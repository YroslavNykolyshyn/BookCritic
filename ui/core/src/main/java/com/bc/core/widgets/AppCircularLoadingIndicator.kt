package com.bc.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bc.core.theme.AppTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppCircularLoadingIndicator(transparentBackground: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (transparentBackground) Color.Transparent else AppTheme.colors.background),
        contentAlignment = Alignment.Center,
    ) {
        LoadingIndicator(
            modifier = Modifier.size(AppTheme.dimensions.iconSizeExtraLarge)
        )
    }
}