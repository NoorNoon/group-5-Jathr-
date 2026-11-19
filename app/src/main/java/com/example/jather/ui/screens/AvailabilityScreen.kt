package com.example.jather.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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

private val IconCalendar: ImageVector = ImageVector.Builder(
    name = "Calendar",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
    ) {
        moveTo(3f, 6f)
        lineTo(21f, 6f)
        lineTo(21f, 20f)
        lineTo(3f, 20f)
        close()
        moveTo(3f, 10f)
        lineTo(21f, 10f)
        moveTo(8f, 3f)
        lineTo(8f, 6f)
        moveTo(16f, 3f)
        lineTo(16f, 6f)
    }
}.build()

private val IconClock: ImageVector = ImageVector.Builder(
    name = "Clock",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
    ) {
        moveTo(12f, 22f)
        curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
        curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
        curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
        curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
        close()
        moveTo(12f, 6f)
        lineTo(12f, 12f)
        lineTo(16f, 14f)
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

@Composable
fun AvailabilityScreen(
    uiState: AvailabilityUiState,
    onBackClick: () -> Unit,
    onDayClick: (String) -> Unit,
    onTimeRangeClick: (String) -> Unit,
    onSaveClick: () -> Unit,
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
            // 1. Top Row
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

            // 3. Header Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = JathrLavender,
                    ),
                    border = BorderStroke(1.dp, JathrBorder),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(12.dp),
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            // TODO: icon
                            Icon(
                                imageVector = IconCalendar,
                                contentDescription = stringResource(R.string.availability_title),
                                tint = JathrPrimary,
                                modifier = Modifier.size(26.dp),
                            )
                        }

                        Column {
                            Text(
                                text = stringResource(R.string.jathr_brand),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = JathrPrimary,
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = stringResource(R.string.availability_title),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = JathrTextMain,
                            )
                        }
                    }
                }
            }

            // 4. White Card Containing Rows
            item {
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
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        if (uiState.days.isEmpty()) {
                            Text(
                                text = stringResource(R.string.no_availability_times),
                                style = MaterialTheme.typography.bodyMedium,
                                color = JathrTextSecondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                            )
                        } else {
                            uiState.days.forEach { day ->
                                // Day Availability Status Row
                                Surface(
                                    onClick = { onDayClick(day.id) },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(14.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    border = BorderStroke(1.dp, JathrBorder),
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 14.dp, vertical = 12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = stringResource(
                                                if (day.isAvailable) R.string.day_available_format
                                                else R.string.day_unavailable_format,
                                                day.dayLabel,
                                            ),
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Medium,
                                            color = JathrTextMain,
                                        )

                                        Box(
                                            modifier = Modifier
                                                .size(36.dp)
                                                .background(
                                                    color = JathrLavender,
                                                    shape = RoundedCornerShape(10.dp),
                                                ),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            // TODO: icon
                                            Icon(
                                                imageVector = IconCalendar,
                                                contentDescription = day.dayLabel,
                                                tint = JathrPrimary,
                                                modifier = Modifier.size(20.dp),
                                            )
                                        }
                                    }
                                }

                                // Time Range Row (when available)
                                if (day.isAvailable) {
                                    Surface(
                                        onClick = { onTimeRangeClick(day.id) },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(14.dp),
                                        color = MaterialTheme.colorScheme.surface,
                                        border = BorderStroke(1.dp, JathrBorder),
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 14.dp, vertical = 12.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                        ) {
                                            Text(
                                                text = if (day.startTime.isNotEmpty() && day.endTime.isNotEmpty()) {
                                                    stringResource(R.string.time_range_format, stringResource(R.string.time_range_label), day.startTime, day.endTime)
                                                } else {
                                                    stringResource(R.string.time_range_label)
                                                },
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = JathrTextMain,
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .size(36.dp)
                                                    .background(
                                                        color = JathrLavender,
                                                        shape = RoundedCornerShape(10.dp),
                                                    ),
                                                contentAlignment = Alignment.Center,
                                            ) {
                                                // TODO: icon
                                                Icon(
                                                    imageVector = IconClock,
                                                    contentDescription = stringResource(R.string.time_range_label),
                                                    tint = JathrPrimary,
                                                    modifier = Modifier.size(20.dp),
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

            // 5. Save Button
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

                Spacer(modifier = Modifier.height(16.dp))

                // 6. Bottom Handle Pill
                Box(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .width(64.dp)
                        .height(4.dp)
                        .background(
                            color = JathrBorder,
                            shape = CircleShape,
                        ),
                )
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
                        dayLabel = "الأحد",
                        isAvailable = true,
                        startTime = "09:00",
                        endTime = "17:00",
                    ),
                    DayAvailabilityUi(
                        id = "2",
                        dayLabel = "الاثنين",
                        isAvailable = false,
                    ),
                ),
            ),
            onBackClick = {},
            onDayClick = {},
            onTimeRangeClick = {},
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
            onDayClick = {},
            onTimeRangeClick = {},
            onSaveClick = {},
        )
    }
}
