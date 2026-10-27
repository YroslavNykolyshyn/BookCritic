package com.bc.core.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bc.core.R
import com.bc.core.theme.AppTheme

@Composable
fun AppDataPickerField(
    startDate: Long?,
    endDate: Long?,
    onStartDateChange: (Long?) -> Unit,
    onEndDateChange: (Long?) -> Unit,
) {
    var showStartedDatePicker by remember { mutableStateOf(false) }
    var showFinishedDatePicker by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = stringResource(R.string.select_reading_period),
            style = MaterialTheme.typography.labelLarge,
            color = AppTheme.colors.brown,
            fontWeight = FontWeight.Medium,
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            AppDateField(
                value = startDate?.let(::formatDate) ?: "Start date",
                valueColor = if (startDate == null) AppTheme.colors.textTint else AppTheme.colors.textColor,
                onClick = { showStartedDatePicker = true },
                modifier = Modifier.weight(1f),
            )

            AppDateField(
                value = endDate?.let(::formatDate) ?: "End date",
                valueColor = if (endDate == null) AppTheme.colors.textTint else AppTheme.colors.textColor,
                onClick = { showFinishedDatePicker = true },
                modifier = Modifier.weight(1f),
            )
        }
    }

    if (showStartedDatePicker) {
        AppDatePicker(
            onDateSelected = { date ->
                onStartDateChange(date)
                showStartedDatePicker = false
            },
            onDismiss = { showStartedDatePicker = false },
        )
    }

    if (showFinishedDatePicker) {
        AppDatePicker(
            onDateSelected = { date ->
                onEndDateChange(date)
                showFinishedDatePicker = false
            },
            onDismiss = { showFinishedDatePicker = false },
        )
    }
}

@Composable
fun AppDatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun formatDate(millis: Long): String {
    return java.text.SimpleDateFormat(
        "dd MMM yyyy",
        java.util.Locale.getDefault(),
    ).format(java.util.Date(millis))
}