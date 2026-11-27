package com.bc.core.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenu
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bc.core.R
import com.bc.core.theme.AppTheme
import com.bc.model.repository.BookGenre
import com.bc.model.repository.BookGenres

@Composable
fun BookGenreDropdown(
    genres: List<BookGenre>,
    selectedGenreId: String?,
    onGenreSelected: (String?) -> Unit,
    onAddCustomGenre: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var customGenreName by remember { mutableStateOf("") }
    val fieldShape = RoundedCornerShape(17.dp)

    val selectedGenre = genres.find {
        it.id == selectedGenreId
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Genre",
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFF79543E),
            fontWeight = FontWeight.Medium,
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = modifier,
        ) {
            OutlinedTextField(
                value = selectedGenre?.displayName().orEmpty(),
                onValueChange = {},
                readOnly = true,
                placeholder = { Text(text = "Select genre", color = Color(0xFFA39484)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor(
                        ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                        enabled = true,
                    )
                    .fillMaxWidth(),
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
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = AppTheme.colors.surface,
            ) {
                DropdownMenuItem(
                    text = { Text("None") },
                    onClick = {
                        onGenreSelected(null)
                        expanded = false
                    },
                )

                HorizontalDivider()

                genres.forEach { genre ->
                    DropdownMenuItem(
                        text = { Text(genre.displayName()) },
                        onClick = {
                            onGenreSelected(genre.id)
                            expanded = false
                        },
                        trailingIcon = {
                            if (genre.id == selectedGenreId) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                )
                            }
                        },
                    )
                }

                HorizontalDivider()

                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Add custom genre")
                        }
                    },
                    onClick = {
                        expanded = false
                        showAddDialog = true
                    },
                )
            }
        }
    }

    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddDialog = false
                customGenreName = ""
            },
            title = {
                Text("Add custom genre")
            },
            containerColor = AppTheme.colors.surface,
            titleContentColor = AppTheme.colors.textPrimary,
            textContentColor = AppTheme.colors.textPrimary,
            text = {
                OutlinedTextField(
                    value = customGenreName,
                    onValueChange = { customGenreName = it },
                    label = { Text("Genre name") },
                    singleLine = true,
                )
            },
            confirmButton = {
                TextButton(
                    enabled = customGenreName.isNotBlank(),
                    onClick = {
                        onAddCustomGenre(customGenreName.trim())
                        customGenreName = ""
                        showAddDialog = false
                    },
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddDialog = false
                        customGenreName = ""
                    },
                ) {
                    Text("Cancel")
                }
            },
        )
    }
}

val PredefinedGenreNames: Map<String, Int> = mapOf(
    BookGenres.FICTION to R.string.genre_fiction,
    BookGenres.FANTASY to R.string.genre_fantasy,
    BookGenres.SCIENCE_FICTION to R.string.genre_science_fiction,
    // ... rest
)

@Composable
fun BookGenre.displayName(): String =
    PredefinedGenreNames[id]?.let { stringResource(it) } ?: name