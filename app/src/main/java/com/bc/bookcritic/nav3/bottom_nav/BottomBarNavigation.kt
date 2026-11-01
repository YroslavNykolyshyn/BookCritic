package com.bc.bookcritic.nav3.bottom_nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingToolbarDefaults
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.bc.core.navigation.Library
import com.bc.core.theme.AppTheme.colors
import com.bc.core.theme.MaterialAppTheme

@Composable
fun BottomBarNavigation(
    selectedDestination: NavKey?,
    onDestinationClick: (NavKey) -> Unit,
) {
    HorizontalFloatingToolbar(
        expanded = true,
        colors = FloatingToolbarDefaults.standardFloatingToolbarColors(
            toolbarContainerColor = colors.bottomBarBackground,
            toolbarContentColor = colors.secondary,
        ),
    ) {
        BottomBarItem.bottomBarItems.forEach { destination ->
            FloatingNavigationItem(
                selected = selectedDestination == destination.key,
                icon = destination.icon,
                label = destination.label,
                onClick = {
                    onDestinationClick(destination.key)
                },
            )
        }
    }
}

@Composable
private fun FloatingNavigationItem(
    selected: Boolean,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = if (selected) colors.surfaceVariant else Color.Transparent,
    ) {
        Row(
            modifier = Modifier
                .height(FloatingToolbarDefaults.ContainerSize)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (selected) colors.primary else colors.secondary,
            )

            AnimatedVisibility(
                visible = selected,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally(),
            ) {
                Row {
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = label,
                        color = colors.primary,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomBarNavigationPreview() {
    MaterialAppTheme {
        BottomBarNavigation(
            selectedDestination = Library,
            onDestinationClick = {},
        )
    }
}