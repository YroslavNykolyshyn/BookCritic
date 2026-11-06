package com.bc.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onAction: () -> Unit,
    enabled: Boolean = true,
    showEditAction: Boolean = false,
    onEditClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(68.dp)
            .background(Color(0xFFFFFCF8))
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        IconButton(
            onClick = onAction,
            modifier = Modifier
                .size(44.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFE8DED2),
                    shape = CircleShape,
                ),
            enabled = enabled,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Go back",
                tint = Color(0xFF34291F),
            )
        }

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF34291F),
            fontWeight = FontWeight.SemiBold,
        )

        if (showEditAction) {
            IconButton(
                modifier = Modifier
                    .border(
                    width = 1.dp,
                    color = Color(0xFFE8DED2),
                    shape = CircleShape,
                ),
                onClick = onEditClick,
                enabled = enabled,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Edit book",
                    tint = Color(0xFF73513D),
                )
            }
        }
    }
}