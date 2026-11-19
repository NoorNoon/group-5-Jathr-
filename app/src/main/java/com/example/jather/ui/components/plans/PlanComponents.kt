package com.example.jather.ui.components.plans

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jather.data.model.Meal
import com.example.jather.data.model.PlanStatus
import com.example.jather.data.model.WeekDay
import com.example.jather.ui.theme.JathrBorder
import com.example.jather.ui.theme.JathrLavender
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary
import kotlinx.coroutines.launch

val PlanBackground = Color(0xFFF7F4FB)
val PlanGreen = Color(0xFF1E6B3E)
val PlanLightGreen = Color(0xFFE4F0E6)
val PlanRed = Color(0xFFA93226)

@Composable
fun RtlScreen(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        content()
    }
}

@Composable
fun PlanChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = modifier
            .clip(shape)
            .background(if (selected) JathrPrimary else Color.White)
            .border(1.dp, if (selected) JathrPrimary else JathrBorder, shape)
            .clickable { onClick() }
            .padding(vertical = 9.dp, horizontal = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else JathrTextMain,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1
        )
    }
}

@Composable
fun PlanDayTabs(
    days: List<WeekDay>,
    selectedDay: WeekDay?,
    onDayClick: (WeekDay) -> Unit
) {
    Row(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (day in days) {
            PlanChip(
                text = day.label,
                selected = day == selectedDay,
                onClick = { onDayClick(day) },
                modifier = Modifier.widthIn(min = 64.dp)
            )
        }
    }
}

@Composable
fun PlanDaySelector(
    selectedDays: List<WeekDay>,
    onDayClick: (WeekDay) -> Unit
) {
    val firstRow = listOf(WeekDay.SUN, WeekDay.MON, WeekDay.TUE, WeekDay.WED)
    val secondRow = listOf(WeekDay.THU, WeekDay.FRI, WeekDay.SAT)

    Column(
        modifier = Modifier.padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (day in firstRow) {
                PlanChip(
                    text = day.label,
                    selected = day in selectedDays,
                    onClick = { onDayClick(day) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (day in secondRow) {
                PlanChip(
                    text = day.label,
                    selected = day in selectedDays,
                    onClick = { onDayClick(day) },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun PlanPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = JathrPrimary
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun PlanSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.5.dp, JathrPrimary),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text = text, color = JathrPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun PlanStatusChip(status: PlanStatus) {
    val textColor = if (status == PlanStatus.DRAFT) JathrPrimary else PlanGreen
    val backgroundColor = if (status == PlanStatus.DRAFT) JathrLavender else PlanLightGreen
    Text(
        text = status.label,
        color = textColor,
        fontSize = 11.sp,
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Composable
fun PlanTotalCalories(calories: Int) {
    Text(
        text = "إجمالي السعرات اليومية: $calories",
        color = JathrTextSecondary,
        fontSize = 12.sp,
        modifier = Modifier.padding(horizontal = 18.dp)
    )
}

@Composable
fun PlanMealCard(meal: Meal) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .background(Color.White, RoundedCornerShape(16.dp))
            .border(1.dp, JathrBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(text = meal.name, color = JathrTextMain, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Text(
            text = meal.items,
            color = JathrTextSecondary,
            fontSize = 13.sp,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Composable
fun PlanNotesBox(notes: String) {
    Text(
        text = if (notes.isEmpty()) "ملاحظات الأخصائي: لا توجد ملاحظات" else "ملاحظات الأخصائي: $notes",
        color = PlanGreen,
        fontSize = 13.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .background(PlanLightGreen, RoundedCornerShape(14.dp))
            .padding(14.dp)
    )
}

@Composable
fun PlanConfirmDialog(
    title: String,
    message: String,
    confirmText: String,
    dismissText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmColor: Color = PlanRed
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = { Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold) },
        text = { Text(text = message, color = JathrTextSecondary, fontSize = 13.sp) },
        confirmButton = {
            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = confirmColor)
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.5.dp, JathrPrimary)
            ) {
                Text(text = dismissText, color = JathrPrimary)
            }
        }
    )
}

@Composable
fun PlanScrollBar(scrollState: ScrollState, modifier: Modifier = Modifier) {
    if (scrollState.maxValue == 0) return

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .width(20.dp)
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    val thumbHeight = size.height / 4f
                    val speed = scrollState.maxValue / (size.height - thumbHeight)
                    scope.launch { scrollState.scrollTo((scrollState.value + dragAmount * speed).toInt()) }
                }
            }
            .drawBehind {
                val barWidth = 6.dp.toPx()
                val x = (size.width - barWidth) / 2
                val thumbHeight = size.height / 4f
                val progress = scrollState.value.toFloat() / scrollState.maxValue
                val thumbTop = progress * (size.height - thumbHeight)

                drawRoundRect(
                    color = JathrLavender,
                    topLeft = Offset(x, 0f),
                    size = Size(barWidth, size.height),
                    cornerRadius = CornerRadius(barWidth / 2)
                )
                drawRoundRect(
                    color = JathrPrimary,
                    topLeft = Offset(x, thumbTop),
                    size = Size(barWidth, thumbHeight),
                    cornerRadius = CornerRadius(barWidth / 2)
                )
            }
    )
}
