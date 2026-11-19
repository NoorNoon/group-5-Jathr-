package com.example.jather.ui.components.specialist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import kotlinx.coroutines.launch

val SpecialistDarkPurple = Color(0xFF3B2A50)
val SpecialistPrimary = Color(0xFF6B4E8C)
val SpecialistLavender = Color(0xFFEDE5F4)
val SpecialistScreenBg = Color(0xFFF7F3FA)
val SpecialistBorder = Color(0xFFD9CCE6)
val SpecialistTextMain = Color(0xFF2B2235)
val SpecialistTextSecondary = Color(0xFF7A7085)
val SpecialistGreen = Color(0xFF2E7D4F)
val SpecialistGreenBg = Color(0xFFE3F2E8)
val SpecialistAmber = Color(0xFF9A6B00)
val SpecialistAmberBg = Color(0xFFFFF1D6)

@Composable
fun SpecialistRtlScreen(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        content()
    }
}

@Composable
fun JathrHeader(
    title: String,
    subtitle: String,
    onBack: () -> Unit,
    endIcon: (@Composable () -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "جذر",
            color = SpecialistPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.CenterStart)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "←", fontSize = 20.sp, color = SpecialistDarkPurple, fontWeight = FontWeight.Bold)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 56.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = SpecialistDarkPurple,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                if (subtitle.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        color = SpecialistTextSecondary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (endIcon != null) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterEnd),
                    contentAlignment = Alignment.Center
                ) {
                    endIcon()
                }
            }
        }
    }
}

@Composable
fun SpecialistPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = SpecialistPrimary),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun SpecialistSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, SpecialistPrimary),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = SpecialistPrimary),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SpecialistPrimary)
    }
}

@Composable
fun SpecialistScrollBar(scrollState: ScrollState, modifier: Modifier = Modifier) {
    if (scrollState.maxValue == 0) return

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .width(22.dp)
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    val thumbHeight = size.height / 4f
                    val speed = scrollState.maxValue / (size.height - thumbHeight)
                    scope.launch { scrollState.scrollTo((scrollState.value + dragAmount * speed).toInt()) }
                }
            }
            .drawBehind {
                val barWidth = 6.dp.toPx()
                // In RTL, Alignment.TopEnd places on physical left edge
                val x = (size.width - barWidth) / 2
                val thumbHeight = size.height / 4f
                val progress = scrollState.value.toFloat() / scrollState.maxValue
                val thumbTop = progress * (size.height - thumbHeight)

                drawRoundRect(
                    color = SpecialistLavender,
                    topLeft = Offset(x, 0f),
                    size = Size(barWidth, size.height),
                    cornerRadius = CornerRadius(barWidth / 2)
                )
                drawRoundRect(
                    color = SpecialistPrimary,
                    topLeft = Offset(x, thumbTop),
                    size = Size(barWidth, thumbHeight),
                    cornerRadius = CornerRadius(barWidth / 2)
                )
            }
    )
}

@Composable
fun SpecialistBottomNav(navController: NavController, selectedIndex: Int = 1) {
    NavigationBar(
        containerColor = SpecialistLavender,
        contentColor = SpecialistPrimary
    ) {
        val items = listOf(
            Triple("الرئيسية", "🏠", Screen.Home.route),
            Triple("العملاء", "👥", "specialist_clients"),
            Triple("الجدول", "📅", Screen.Appointments.route),
            Triple("حسابي", "👤", Screen.Profile.route)
        )
        items.forEachIndexed { index, (label, emoji, route) ->
            val isSelected = index == selectedIndex
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (route.startsWith("specialist_")) {
                        navController.navigate(route) {
                            popUpTo("specialist_clients") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) SpecialistPrimary else SpecialistTextSecondary
                    )
                },
                icon = {
                    Text(text = emoji, fontSize = 18.sp)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White
                )
            )
        }
    }
}

@Composable
fun JathrScrollScreen(
    navController: NavController,
    title: String,
    subtitle: String,
    onBack: () -> Unit,
    endIcon: (@Composable () -> Unit)? = null,
    selectedBottomTab: Int = 1,
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable ColumnScope.() -> Unit
) {
    SpecialistRtlScreen {
        Scaffold(
            bottomBar = {
                SpecialistBottomNav(navController = navController, selectedIndex = selectedBottomTab)
            },
            containerColor = SpecialistScreenBg
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .padding(bottom = 24.dp)
                ) {
                    JathrHeader(title = title, subtitle = subtitle, onBack = onBack, endIcon = endIcon)
                    Spacer(modifier = Modifier.height(8.dp))
                    content()
                }

                SpecialistScrollBar(
                    scrollState = scrollState,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxHeight()
                )
            }
        }
    }
}
