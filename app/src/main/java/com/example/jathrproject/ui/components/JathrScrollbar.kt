package com.example.jathrproject.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.jathrproject.ui.theme.BorderColor
import com.example.jathrproject.ui.theme.PrimaryPurple
import kotlinx.coroutines.launch

@Composable
fun JathrScrollableContainer(
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var containerHeightPx by remember { mutableStateOf(0) }
    val density = LocalDensity.current

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Row(modifier = modifier.fillMaxSize()) {
            // Literal left-edge custom scrollbar with 22dp touch area
            Box(
                modifier = Modifier
                    .width(22.dp)
                    .fillMaxHeight()
                    .onGloballyPositioned { containerHeightPx = it.size.height }
                    .pointerInput(scrollState, containerHeightPx) {
                        detectVerticalDragGestures { change, dragAmount ->
                            change.consume()
                            if (containerHeightPx > 0 && scrollState.maxValue > 0) {
                                val scrollRatio = scrollState.maxValue.toFloat() / containerHeightPx
                                val targetScroll = scrollState.value + (dragAmount * scrollRatio)
                                coroutineScope.launch {
                                    scrollState.scrollTo(
                                        targetScroll.coerceIn(0f, scrollState.maxValue.toFloat()).toInt()
                                    )
                                }
                            }
                        }
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                if (scrollState.maxValue > 0 && containerHeightPx > 0) {
                    val viewPortRatio = containerHeightPx.toFloat() / (containerHeightPx + scrollState.maxValue).toFloat()
                    val thumbHeightPx = (containerHeightPx * viewPortRatio).coerceAtLeast(with(density) { 36.dp.toPx() })
                    val scrollableRatio = scrollState.value.toFloat() / scrollState.maxValue.toFloat()
                    val thumbOffsetYPx = (containerHeightPx - thumbHeightPx) * scrollableRatio

                    val thumbHeightDp = with(density) { thumbHeightPx.toDp() }

                    // Faint track background (2dp wide)
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .fillMaxHeight()
                            .background(BorderColor.copy(alpha = 0.5f), shape = RoundedCornerShape(1.dp))
                    )

                    // 6dp rounded purple thumb
                    Box(
                        modifier = Modifier
                            .offset { IntOffset(0, thumbOffsetYPx.toInt()) }
                            .width(6.dp)
                            .height(thumbHeightDp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(PrimaryPurple)
                    )
                }
            }

            // Main Content Area inside RTL layout direction
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(scrollState)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}
