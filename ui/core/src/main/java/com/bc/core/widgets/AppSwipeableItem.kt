package com.bc.core.widgets

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.bc.core.theme.AppTheme.colors

@Composable
fun SwipeableBookItem(
    bookId: String,
    isFavorite: Boolean,
    onPinClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit,
) {
    val actionWidth = 80.dp
    val density = LocalDensity.current
    val actionWidthPx = with(density) { actionWidth.toPx() }

    var dragOffset by remember(bookId) {
        mutableFloatStateOf(0f)
    }

    var isDragging by remember(bookId) {
        mutableStateOf(false)
    }

    val animatedOffset by animateDpAsState(
        targetValue = with(density) { dragOffset.toDp() },
        animationSpec = spring(),
        label = "bookSwipeOffset",
    )

    val currentOffset = if (isDragging) {
        with(density) { dragOffset.toDp() }
    } else {
        animatedOffset
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
    ) {
        Box(modifier = Modifier.matchParentSize()) {
            if (dragOffset > 0f) {
                SwipeActionButton(
                    icon = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentColor = if (isFavorite) {
                        Color(0xFFAD3659)
                    } else {
                        Color.White
                    },
                    contentDescription = if (isFavorite) {
                        "Unpin book"
                    } else {
                        "Pin book"
                    },
                    isActive = isFavorite,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .width(actionWidth)
                        .fillMaxHeight(),
                    onClick = onPinClick,
                )
            }
        }

        content(
            Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .offset {
                    IntOffset(
                        x = with(density) { currentOffset.roundToPx() },
                        y = 0,
                    )
                }
                .pointerInput(bookId, actionWidthPx) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            isDragging = true

                            dragOffset = (dragOffset + dragAmount)
                                .coerceIn(0f, actionWidthPx)

                            change.consume()
                        },
                        onDragEnd = {
                            isDragging = false

                            dragOffset = if (dragOffset > actionWidthPx / 2) {
                                actionWidthPx
                            } else {
                                0f
                            }
                        },
                        onDragCancel = {
                            isDragging = false
                            dragOffset = 0f
                        },
                    )
                },
        )
    }
}

@Composable
private fun SwipeActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentColor: Color,
    contentDescription: String,
    isActive: Boolean = false,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        color = if (isActive) colors.surface else colors.primary,
        contentColor = contentColor,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}