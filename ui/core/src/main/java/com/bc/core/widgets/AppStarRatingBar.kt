package com.bc.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bc.core.R
import com.bc.core.theme.AppTheme

@Composable
fun AppStarRatingBar(
    modifier: Modifier = Modifier,
    label: String,
    rating: Float,
    maxRating: Int = 5,
    onRatingChanged: (Float) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = AppTheme.colors.primary,
            fontWeight = FontWeight.Medium,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    AppTheme.colors.inputBackground,
                    shape = RoundedCornerShape(17.dp),
                )
                .border(
                    width = 1.dp,
                    color = AppTheme.colors.border,
                    shape = RoundedCornerShape(17.dp),
                )
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
            ) {
                for (i in 1..maxRating) {
                    val isFilled = rating >= i.toFloat()

                    IconButton(
                        onClick = { onRatingChanged(i.toFloat()) },
                    ) {
                        Icon(
                            modifier = Modifier.size(AppTheme.dimensions.iconSizeTiny),
                            imageVector = if (isFilled) {
                                Icons.Filled.Star
                            } else {
                                Icons.Outlined.Star
                            },
                            contentDescription = stringResource(
                                R.string.rating_star_description,
                                i,
                                maxRating,
                            ),
                            tint = if (isFilled) {
                                AppTheme.colors.primary
                            } else {
                                AppTheme.colors.border
                            },
                        )
                    }
                }
            }

            Text(
                text = "${rating.toInt()}/$maxRating",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.colors.primary,
            )
        }
    }
}