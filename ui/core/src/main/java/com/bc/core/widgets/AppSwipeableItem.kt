package com.bc.core.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
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
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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

enum class BookSwipeAction {
    PIN,
    EDIT,
}

data class BookSwipeReveal(
    val bookId: String,
    val action: BookSwipeAction,
)

@Composable
fun SwipeableBookItem(
    bookId: String,
    activeSwipe: BookSwipeReveal?,
    onRevealChange: (BookSwipeReveal?) -> Unit,
    onPinClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit,
) {
    val actionWidth = 80.dp
    val density = LocalDensity.current
    val actionWidthPx = with(density) { actionWidth.toPx() }

    var dragOffset by remember(bookId) {
        mutableFloatStateOf(0f)
    }

    val animatedOffset by animateDpAsState(
        targetValue = with(density) { dragOffset.toDp() },
        animationSpec = spring(),
        label = "bookSwipeOffset",
    )

    LaunchedEffect(activeSwipe) {
        val action = activeSwipe
            ?.takeIf { it.bookId == bookId }
            ?.action

        dragOffset = when (action) {
            BookSwipeAction.PIN -> actionWidthPx
            BookSwipeAction.EDIT -> -actionWidthPx
            null -> 0f
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
    ) {
        Box(
            modifier = Modifier.matchParentSize(),
        ) {
            AnimatedVisibility(
                visible = dragOffset > 0f,
                modifier = Modifier.align(Alignment.CenterStart),
                enter = EnterTransition.None,
                exit = ExitTransition.None,
            ) {
                SwipeActionButton(
                    icon = Icons.Outlined.Favorite,
                    contentColor = Color(0xFFAD3659),
                    contentDescription = "Pin book",
                    modifier = Modifier
                        .width(actionWidth)
                        .fillMaxHeight(),
                    onClick = {
                        onPinClick()
                        onRevealChange(null)
                    },
                )
            }

            AnimatedVisibility(
                visible = dragOffset < 0f,
                modifier = Modifier.align(Alignment.CenterEnd),
                enter = EnterTransition.None,
                exit = ExitTransition.None,
            ) {
                SwipeActionButton(
                    icon = Icons.Outlined.Edit,
                    contentColor = Color(0xFF405D85),
                    contentDescription = "Edit book",
                    modifier = Modifier
                        .width(actionWidth)
                        .fillMaxHeight(),
                    onClick = {
                        onEditClick()
                        onRevealChange(null)
                    },
                )
            }
        }

        content(
            Modifier
                .fillMaxWidth()
                .zIndex(1f)
                .offset {
                    IntOffset(
                        x = animatedOffset.roundToPx(),
                        y = 0,
                    )
                }
                .pointerInput(bookId, actionWidthPx) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            dragOffset = (dragOffset + dragAmount)
                                .coerceIn(
                                    -actionWidthPx,
                                    actionWidthPx,
                                )

                            change.consume()
                        },
                        onDragEnd = {
                            val targetOffset = when {
                                dragOffset > actionWidthPx / 2 ->
                                    actionWidthPx

                                dragOffset < -actionWidthPx / 2 ->
                                    -actionWidthPx

                                else -> 0f
                            }

                            dragOffset = targetOffset

                            val action = when (targetOffset) {
                                actionWidthPx -> BookSwipeAction.PIN
                                -actionWidthPx -> BookSwipeAction.EDIT
                                else -> null
                            }

                            onRevealChange(
                                action?.let {
                                    BookSwipeReveal(
                                        bookId = bookId,
                                        action = it,
                                    )
                                },
                            )
                        },
                        onDragCancel = {
                            dragOffset = when {
                                activeSwipe?.bookId != bookId -> 0f
                                activeSwipe.action == BookSwipeAction.PIN ->
                                    actionWidthPx

                                else -> -actionWidthPx
                            }
                        },
                    )
                },
        )
    }
}

@Composable
private fun SwipeActionButton(
    icon: ImageVector,
    contentColor: Color,
    contentDescription: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        color = colors.primary,
        contentColor = contentColor,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}