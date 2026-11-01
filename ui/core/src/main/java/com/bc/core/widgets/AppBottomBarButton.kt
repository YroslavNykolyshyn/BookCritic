package com.bc.core.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bc.core.theme.AppTheme
import com.bc.core.theme.AppTheme.colors

@Composable
fun AppBottomBarButton(
    visible: Boolean,
    onSaveClicked: () -> Unit,
    buttonEnabled: Boolean,
    readBookCount: Int? = null,
) {
    if (!visible) {
        // Save
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimensions.paddingExtraSmall),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Button(
                onClick = {
                    onSaveClicked()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(17.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary,
                    contentColor = Color.White,
                ),
                enabled = !buttonEnabled,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "Finish and save",
                    fontWeight = FontWeight.Medium,
                )
            }

            readBookCount?.let {
                Text(
                    text = "Your read shelf has $readBookCount books. Make it ${readBookCount + 1}.",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.secondary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}