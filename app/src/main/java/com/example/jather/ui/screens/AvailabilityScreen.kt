package com.example.jather.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jather.R
import com.example.jather.ui.theme.*

data class DayAvailabilityUi(
    val id: String = "",
    val dayLabel: String = "",
    val isAvailable: Boolean = false,
    val startTime: String = "",
    val endTime: String = "",
)

data class AvailabilityUiState(
    val days: List<DayAvailabilityUi> = emptyList(),
)

// Programmatic ImageVectors using Compose UI Graphics
private val IconArrowBack: ImageVector = ImageVector.Builder(
    name = "ArrowBack",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2.5f,
    ) {
        moveTo(15f, 18f)
        lineTo(9f, 12f)
        lineTo(15f, 6f)
    }
}.build()

private val IconCheckMark: ImageVector = ImageVector.Builder(
    name = "CheckMark",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2.5f,
    ) {
        moveTo(5f, 12f)
        lineTo(10f, 17f)
        lineTo(19f, 6f)
    }
}.build()

private val availableHours = listOf(
    "06:00", "07:00", "08:00", "09:00", "10:00", "11:00",
    "12:00", "13:00", "14:00", "15:00", "16:00", "17:00",
    "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"
)

@Composable
fun AvailabilityScreen(
    uiState: AvailabilityUiState,
    onBackClick: () -> Unit = {},
    onDayToggle: (String) -> Unit = {},
    onStartTimeSelected: (String, String) -> Unit = { _, _ -> },
    onEndTimeSelected: (String, String) -> Unit = { _, _ -> },
    onDayClick: (String) -> Unit = {},
    onTimeRangeClick: (String) -> Unit = {},
    onSaveClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(JathrLavender),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 1. Top Row (Back button + Logo)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp),
                            ),
                    ) {
                        // TODO: icon
                        Icon(
                            imageVector = IconArrowBack,
                            contentDescription = stringResource(R.string.back_button_desc),
                            tint = JathrPrimary,
                        )
                    }

                    // TODO: logo - I will write it
                    Text(
                        text = stringResource(R.string.jathr_brand),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = JathrPrimary,
                    )

                    Spacer(modifier = Modifier.width(44.dp))
                }
            }

            // 2. Screen Title
            item {
                Text(
                    text = stringResource(R.string.availability_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = JathrTextMain,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 4.dp),
                )
            }

            // 3. Day Cards
            if (uiState.days.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                        border = BorderStroke(1.dp, JathrBorder),
                    ) {
                        Text(
                            text = stringResource(R.string.no_availability_times),
                            style = MaterialTheme.typography.bodyMedium,
                            color = JathrTextSecondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                        )
                    }
                }
            } else {
                items(uiState.days) { day ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                        border = BorderStroke(1.dp, JathrBorder),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            // Day Row: Name on start (right in RTL), Switch on end (left in RTL)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = day.dayLabel,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                )

                                Switch(
                                    checked = day.isAvailable,
                                    onCheckedChange = {
                                        // TODO: logic - I will write it
                                        onDayToggle(day.id)
                                        onDayClick(day.id)
                                    },
                                    colors = SwitchDefaults.colors(
                                        checkedTrackColor = JathrPrimary,
                                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                        uncheckedTrackColor = JathrBorder.copy(alpha = 0.4f),
                                    ),
                                )
                            }

                            // When isAvailable == true: Time Pickers (LazyRow for "من" and "إلى")
                            if (day.isAvailable) {
                                HorizontalDivider(color = JathrBorder, thickness = 0.5.dp)

                                // "من" (From)
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(
                                        text = stringResource(R.string.time_from_label),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = JathrTextSecondary,
                                    )

                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        contentPadding = PaddingValues(vertical = 2.dp),
                                    ) {
                                        items(availableHours) { time ->
                                            val isSelected = day.startTime == time
                                            Surface(
                                                onClick = {
                                                    // TODO: logic - I will write it
                                                    onStartTimeSelected(day.id, time)
                                                    onTimeRangeClick(day.id)
                                                },
                                                shape = RoundedCornerShape(10.dp),
                                                color = if (isSelected) JathrPrimary else JathrBorder.copy(alpha = 0.3f),
                                            ) {
                                                Text(
                                                    text = time,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else JathrTextSecondary,
                                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                                )
                                            }
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                // "إلى" (To)
                                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(
                                        text = stringResource(R.string.time_to_label),
                                        style = MaterialTheme.typography.labelMedium,
                                        color = JathrTextSecondary,
                                    )

                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        contentPadding = PaddingValues(vertical = 2.dp),
                                    ) {
                                        items(availableHours) { time ->
                                            val isSelected = day.endTime == time
                                            Surface(
                                                onClick = {
                                                    // TODO: logic - I will write it
                                                    onEndTimeSelected(day.id, time)
                                                    onTimeRangeClick(day.id)
                                                },
                                                shape = RoundedCornerShape(10.dp),
                                                color = if (isSelected) JathrPrimary else JathrBorder.copy(alpha = 0.3f),
                                            ) {
                                                Text(
                                                    text = time,
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else JathrTextSecondary,
                                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. Save Button
            item {
                Spacer(modifier = Modifier.height(4.dp))

                // TODO: logic - I will write it
                Button(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = JathrPrimary,
                    ),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        // TODO: icon
                        Icon(
                            imageVector = IconCheckMark,
                            contentDescription = stringResource(R.string.save_availability_button),
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.save_availability_button),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar", heightDp = 900)
@Composable
fun AvailabilityScreenSamplePreview() {
    JatherTheme {
        AvailabilityScreen(
            uiState = AvailabilityUiState(
                days = listOf(
                    DayAvailabilityUi(
                        id = "1",
                        dayLabel = stringResource(R.string.day_sunday),
                        isAvailable = true,
                        startTime = "09:00",
                        endTime = "17:00",
                    ),
                    DayAvailabilityUi(
                        id = "2",
                        dayLabel = stringResource(R.string.day_monday),
                        isAvailable = false,
                    ),
                    DayAvailabilityUi(
                        id = "3",
                        dayLabel = stringResource(R.string.day_tuesday),
                        isAvailable = true,
                        startTime = "09:00",
                        endTime = "17:00",
                    ),
                    DayAvailabilityUi(
                        id = "4",
                        dayLabel = stringResource(R.string.day_wednesday),
                        isAvailable = false,
                    ),
                    DayAvailabilityUi(
                        id = "5",
                        dayLabel = stringResource(R.string.day_thursday),
                        isAvailable = false,
                    ),
                    DayAvailabilityUi(
                        id = "6",
                        dayLabel = stringResource(R.string.day_friday),
                        isAvailable = false,
                    ),
                    DayAvailabilityUi(
                        id = "7",
                        dayLabel = stringResource(R.string.day_saturday),
                        isAvailable = false,
                    ),
                ),
            ),
            onBackClick = {},
            onDayToggle = {},
            onStartTimeSelected = { _, _ -> },
            onEndTimeSelected = { _, _ -> },
            onSaveClick = {},
        )
    }
}

@Preview(showBackground = true, locale = "ar", heightDp = 900)
@Composable
fun AvailabilityScreenEmptyPreview() {
    JatherTheme {
        AvailabilityScreen(
            uiState = AvailabilityUiState(),
            onBackClick = {},
            onDayToggle = {},
            onStartTimeSelected = { _, _ -> },
            onEndTimeSelected = { _, _ -> },
            onSaveClick = {},
        )
    }
}
