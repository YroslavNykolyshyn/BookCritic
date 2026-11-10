package com.bc.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bc.core.theme.AppTheme

@Composable
fun AppAddBookCard(
    modifier: Modifier = Modifier,
    label: String,
    description: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(AppTheme.colors.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(vertical = AppTheme.dimensions.paddingExtraMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(AppTheme.colors.primary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = AppTheme.colors.primary,
                modifier = Modifier.size(28.dp),
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = AppTheme.colors.textPrimary,
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.colors.secondary,
        )
    }
}