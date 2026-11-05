package com.bc.core.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bc.core.theme.AppTheme

@Composable
fun AppTextInput(
    label: String,
    value: String,
    onAction: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    minLines: Int = 1,
    singleLine: Boolean = true,
    height: Dp? = null,
    readOnly: Boolean = false,
) {
    val fieldShape = RoundedCornerShape(17.dp)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFF79543E),
            fontWeight = FontWeight.Medium,
        )

        OutlinedTextField(
            value = value,
            onValueChange = onAction,
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (height != null) {
                        Modifier.height(height)
                    } else {
                        Modifier
                    }
                ),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color(0xFFA39484),
                )
            },
            singleLine = singleLine,
            minLines = minLines,
            shape = fieldShape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AppTheme.colors.textPrimary,
                unfocusedTextColor = AppTheme.colors.textPrimary,
                focusedContainerColor = AppTheme.colors.inputBackground,
                unfocusedContainerColor = Color(0xFFFFFCF8),
                focusedBorderColor = Color(0xFF79543E),
                unfocusedBorderColor = Color(0xFFE8DED2),
                cursorColor = Color(0xFF79543E),
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            readOnly = readOnly,
        )
    }
}