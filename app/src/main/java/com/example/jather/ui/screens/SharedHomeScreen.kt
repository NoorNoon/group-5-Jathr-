package com.example.jather.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jather.R
import com.example.jather.ui.theme.*

data class NextAppointmentData(
    val id: String = "",
    val specialistName: String = "",
    val specialistRole: String = "",
    val dateText: String = "",
    val timeText: String = "",
    val statusText: String = "",
)

data class WeightProgressData(
    val currentWeight: String = "",
    val adherenceText: String = "",
    val progressPercentText: String = "",
    val progressFraction: Float = 0f,
    val startWeightText: String = "",
    val lostWeightText: String = "",
    val goalWeightText: String = "",
)

data class SpecialistNoteData(
    val specialistName: String = "",
    val timeText: String = "",
    val noteText: String = "",
)

data class ProgressOverviewData(
    val weeklyChange: String = "",
    val weeksCount: String = "",
    val adherencePercent: String = "",
)

data class AppointmentItemData(
    val id: String = "",
    val clientName: String = "",
    val serviceType: String = "",
    val timeText: String = "",
    val statusText: String = "",
)

data class PendingFollowUpData(
    val id: String = "",
    val clientName: String = "",
    val detailsText: String = "",
)

data class SharedHomeUiState(
    val isSpecialist: Boolean = false,
    val userName: String = "",
    val hasUnreadNotifications: Boolean = true,
    val nextAppointment: NextAppointmentData = NextAppointmentData(),
    val weightProgress: WeightProgressData = WeightProgressData(),
    val latestNote: SpecialistNoteData = SpecialistNoteData(),
    val progressOverview: ProgressOverviewData = ProgressOverviewData(),
    val todayAppointmentsCount: Int = 0,
    val activeClientsCount: Int = 0,
    val newFollowUpsCount: Int = 0,
    val todayAppointmentsList: List<AppointmentItemData> = emptyList(),
    val pendingFollowUpsList: List<PendingFollowUpData> = emptyList(),
)

// Programmatic ImageVectors using Compose UI Graphics
private val IconBell: ImageVector = ImageVector.Builder(
    name = "Bell",
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
        curveTo(13.1f, 22f, 14f, 21.1f, 14f, 20f)
        lineTo(10f, 20f)
        curveTo(10f, 21.1f, 10.9f, 22f, 12f, 22f)
        close()
        moveTo(18f, 16f)
        lineTo(18f, 11f)
        curveTo(18f, 7.93f, 16.37f, 5.36f, 13.5f, 4.68f)
        lineTo(13.5f, 4f)
        curveTo(13.5f, 3.17f, 12.83f, 2.5f, 12f, 2.5f)
        curveTo(11.17f, 2.5f, 10.5f, 3.17f, 10.5f, 4f)
        lineTo(10.5f, 4.68f)
        curveTo(7.64f, 5.36f, 6f, 7.92f, 6f, 11f)
        lineTo(6f, 16f)
        lineTo(4f, 18f)
        lineTo(4f, 19f)
        lineTo(20f, 19f)
        lineTo(20f, 18f)
        lineTo(18f, 16f)
        close()
    }
}.build()

private val IconPerson: ImageVector = ImageVector.Builder(
    name = "Person",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
    ) {
        moveTo(12f, 4f)
        curveTo(9.79f, 4f, 8f, 5.79f, 8f, 8f)
        curveTo(8f, 10.21f, 9.79f, 12f, 12f, 12f)
        curveTo(14.21f, 12f, 16f, 10.21f, 16f, 8f)
        curveTo(16f, 5.79f, 14.21f, 4f, 12f, 4f)
        close()
        moveTo(12f, 14f)
        curveTo(8f, 14f, 4f, 16f, 4f, 20f)
        lineTo(20f, 20f)
        curveTo(20f, 16f, 16f, 14f, 12f, 14f)
        close()
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

private val IconFolder: ImageVector = ImageVector.Builder(
    name = "Folder",
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
        lineTo(10f, 6f)
        lineTo(12f, 8f)
        lineTo(21f, 8f)
        lineTo(21f, 19f)
        lineTo(3f, 19f)
        close()
    }
}.build()

private val IconCheckCircle: ImageVector = ImageVector.Builder(
    name = "CheckCircle",
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
        moveTo(8f, 12f)
        lineTo(11f, 15f)
        lineTo(16f, 9f)
    }
}.build()

private val IconChatNote: ImageVector = ImageVector.Builder(
    name = "ChatNote",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f,
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
    ) {
        moveTo(4f, 4f)
        lineTo(20f, 4f)
        lineTo(20f, 16f)
        lineTo(8f, 16f)
        lineTo(4f, 20f)
        close()
    }
}.build()

@Composable
fun SharedHomeScreen(
    uiState: SharedHomeUiState,
    onRoleSelected: (Boolean) -> Unit,
    onNotificationsClick: () -> Unit,
    onNextAppointmentClick: () -> Unit,
    onViewPlanClick: () -> Unit,
    onViewProgressClick: () -> Unit,
    onBookAppointmentClick: () -> Unit,
    onFilesClick: () -> Unit,
    onFollowUpClick: () -> Unit,
    onViewScheduleClick: () -> Unit,
    onViewAllFollowUpsClick: () -> Unit,
    onAppointmentClick: (String) -> Unit,
    onFollowUpItemClick: (String) -> Unit,
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // 1. Header (Logo, Bell, Greeting, Subtitle, Role Toggle)
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Top Bar (Bell at start / right in RTL, Logo centered)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box {
                            IconButton(
                                onClick = onNotificationsClick,
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(
                                        color = MaterialTheme.colorScheme.surface,
                                        shape = RoundedCornerShape(12.dp),
                                    ),
                            ) {
                                // TODO: icon
                                Icon(
                                    imageVector = IconBell,
                                    contentDescription = stringResource(R.string.notifications_desc),
                                    tint = JathrPrimary,
                                )
                            }
                            if (uiState.hasUnreadNotifications) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.error,
                                            shape = CircleShape,
                                        )
                                        .align(Alignment.TopEnd),
                                )
                            }
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

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(R.string.greeting_format, uiState.userName.ifEmpty { "سارة" }),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = JathrPrimary,
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = stringResource(
                            if (!uiState.isSpecialist) R.string.client_home_subtitle
                            else R.string.specialist_home_subtitle
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = JathrTextSecondary,
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Role Toggle ("عميل" is first / right in RTL, "الأخصائي" second)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp),
                            )
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        // Client Tab
                        Surface(
                            onClick = { onRoleSelected(false) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = if (!uiState.isSpecialist) JathrPrimary else Color.Transparent,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                // TODO: icon
                                Icon(
                                    imageVector = IconPerson,
                                    contentDescription = stringResource(R.string.client_tab),
                                    tint = if (!uiState.isSpecialist) MaterialTheme.colorScheme.onPrimary else JathrPrimary,
                                    modifier = Modifier.size(18.dp),
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = stringResource(R.string.client_tab),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (!uiState.isSpecialist) MaterialTheme.colorScheme.onPrimary else JathrPrimary,
                                )
                            }
                        }

                        // Specialist Tab
                        Surface(
                            onClick = { onRoleSelected(true) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(12.dp),
                            color = if (uiState.isSpecialist) JathrPrimary else Color.Transparent,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                // TODO: icon
                                Icon(
                                    imageVector = IconPerson,
                                    contentDescription = stringResource(R.string.specialist_tab),
                                    tint = if (uiState.isSpecialist) MaterialTheme.colorScheme.onPrimary else JathrPrimary,
                                    modifier = Modifier.size(18.dp),
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = stringResource(R.string.specialist_tab),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (uiState.isSpecialist) MaterialTheme.colorScheme.onPrimary else JathrPrimary,
                                )
                            }
                        }
                    }
                }
            }

            // 2. Client Content vs Specialist Content
            if (!uiState.isSpecialist) {
                // Client Card 1: Next Appointment
                item {
                    Card(
                        onClick = onNextAppointmentClick,
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
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = stringResource(R.string.next_appointment_title),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                )
                                if (uiState.nextAppointment.specialistName.isNotEmpty() || uiState.nextAppointment.id.isNotEmpty()) {
                                    // Status chip
                                    // TODO: color
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = JathrLavender,
                                    ) {
                                        Text(
                                            text = uiState.nextAppointment.statusText.ifEmpty { stringResource(R.string.status_confirmed) },
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = JathrPrimary,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            if (uiState.nextAppointment.specialistName.isEmpty() && uiState.nextAppointment.id.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.no_next_appointment),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = JathrTextSecondary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                )
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(44.dp)
                                                .background(
                                                    color = JathrPrimary,
                                                    shape = CircleShape,
                                                ),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(
                                                text = uiState.nextAppointment.specialistName.trim().take(1).ifEmpty { "ن" },
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onPrimary,
                                            )
                                        }
                                        Column {
                                            Text(
                                                text = uiState.nextAppointment.specialistName,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = JathrTextMain,
                                            )
                                            Text(
                                                text = uiState.nextAppointment.specialistRole,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = JathrTextSecondary,
                                            )
                                        }
                                    }

                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = uiState.nextAppointment.dateText,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = JathrTextMain,
                                        )
                                        Text(
                                            text = uiState.nextAppointment.timeText,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = JathrTextSecondary,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Client Card 2: Current Weight Progress
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
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = stringResource(R.string.current_weight_title),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                )
                                if (uiState.weightProgress.adherenceText.isNotEmpty()) {
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = JathrLavender,
                                    ) {
                                        Text(
                                            text = uiState.weightProgress.adherenceText,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = JathrPrimary,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (uiState.weightProgress.currentWeight.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.no_weight_data),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = JathrTextSecondary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                )
                            } else {
                                Text(
                                    text = uiState.weightProgress.currentWeight,
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrPrimary,
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                LinearProgressIndicator(
                                    progress = { uiState.weightProgress.progressFraction.coerceIn(0f, 1f) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(8.dp),
                                    color = JathrPrimary,
                                    trackColor = JathrLavender,
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                if (uiState.weightProgress.progressPercentText.isNotEmpty()) {
                                    Text(
                                        text = uiState.weightProgress.progressPercentText,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = JathrPrimary,
                                        fontWeight = FontWeight.Bold,
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = uiState.weightProgress.startWeightText,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = JathrTextSecondary,
                                    )
                                    Text(
                                        text = uiState.weightProgress.lostWeightText,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = JathrTextSecondary,
                                    )
                                    Text(
                                        text = uiState.weightProgress.goalWeightText,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = JathrTextSecondary,
                                    )
                                }
                            }
                        }
                    }
                }

                // Client Card 3: Latest Note
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
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = stringResource(R.string.latest_specialist_note_title),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                )
                                TextButton(onClick = onViewPlanClick) {
                                    Text(
                                        text = stringResource(R.string.my_plan_button),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = JathrPrimary,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            if (uiState.latestNote.noteText.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.no_specialist_notes),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = JathrTextSecondary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                )
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    ) {
                                        // TODO: icon
                                        Icon(
                                            imageVector = IconChatNote,
                                            contentDescription = stringResource(R.string.latest_specialist_note_title),
                                            tint = JathrPrimary,
                                            modifier = Modifier.size(20.dp),
                                        )
                                        Text(
                                            text = uiState.latestNote.specialistName,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = JathrTextMain,
                                        )
                                    }
                                    Text(
                                        text = uiState.latestNote.timeText,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = JathrTextSecondary,
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = uiState.latestNote.noteText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = JathrTextMain,
                                )
                            }
                        }
                    }
                }

                // Client Card 4: Progress Overview
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
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = stringResource(R.string.progress_overview_title),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                )
                                TextButton(onClick = onViewProgressClick) {
                                    Text(
                                        text = stringResource(R.string.my_progress_button),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = JathrPrimary,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (uiState.progressOverview.weeklyChange.isEmpty() && uiState.progressOverview.weeksCount.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.no_progress_data),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = JathrTextSecondary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 12.dp),
                                )
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Surface(
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp),
                                        color = JathrLavender,
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(10.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        ) {
                                            Text(
                                                text = uiState.progressOverview.weeklyChange,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = JathrPrimary,
                                            )
                                            Text(
                                                text = "كجم/أسبوع",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = JathrTextSecondary,
                                            )
                                        }
                                    }

                                    Surface(
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp),
                                        color = JathrLavender,
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(10.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        ) {
                                            Text(
                                                text = uiState.progressOverview.weeksCount,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = JathrPrimary,
                                            )
                                            Text(
                                                text = "أسابيع",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = JathrTextSecondary,
                                            )
                                        }
                                    }

                                    Surface(
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp),
                                        color = JathrLavender,
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(10.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        ) {
                                            Text(
                                                text = uiState.progressOverview.adherencePercent,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = JathrPrimary,
                                            )
                                            Text(
                                                text = "الالتزام",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = JathrTextSecondary,
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                // TODO: real chart
                                Canvas(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(44.dp),
                                ) {
                                    val path = Path().apply {
                                        moveTo(0f, size.height * 0.7f)
                                        quadraticTo(
                                            size.width * 0.35f, size.height * 0.9f,
                                            size.width * 0.65f, size.height * 0.3f,
                                        )
                                        lineTo(size.width, size.height * 0.2f)
                                    }
                                    drawPath(
                                        path = path,
                                        color = JathrPrimary,
                                        style = Stroke(width = 3.dp.toPx()),
                                    )
                                }
                            }
                        }
                    }
                }

                // Client Item 5: Action Buttons Row
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        OutlinedButton(
                            onClick = onBookAppointmentClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                            border = BorderStroke(1.dp, JathrBorder),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                            ) {
                                // TODO: icon
                                Icon(
                                    imageVector = IconCalendar,
                                    contentDescription = stringResource(R.string.book_appointment_action),
                                    tint = JathrPrimary,
                                    modifier = Modifier.size(16.dp),
                                )
                                Text(
                                    text = stringResource(R.string.book_appointment_action),
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = onFilesClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                            border = BorderStroke(1.dp, JathrBorder),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                            ) {
                                // TODO: icon
                                Icon(
                                    imageVector = IconFolder,
                                    contentDescription = stringResource(R.string.my_files_action),
                                    tint = JathrPrimary,
                                    modifier = Modifier.size(16.dp),
                                )
                                Text(
                                    text = stringResource(R.string.my_files_action),
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = onFollowUpClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                            border = BorderStroke(1.dp, JathrBorder),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                            ) {
                                // TODO: icon
                                Icon(
                                    imageVector = IconCheckCircle,
                                    contentDescription = stringResource(R.string.follow_up_action),
                                    tint = JathrPrimary,
                                    modifier = Modifier.size(16.dp),
                                )
                                Text(
                                    text = stringResource(R.string.follow_up_action),
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                    maxLines = 1,
                                    softWrap = false,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    }
                }
            } else {
                // Specialist Content
                // Specialist 1: Three Stat Cards
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                            border = BorderStroke(1.dp, JathrBorder),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = uiState.todayAppointmentsCount.toString(),
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrPrimary,
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = stringResource(R.string.today_appointments_title),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = JathrTextSecondary,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }

                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                            border = BorderStroke(1.dp, JathrBorder),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = uiState.activeClientsCount.toString(),
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrPrimary,
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = stringResource(R.string.active_clients_title),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = JathrTextSecondary,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }

                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                            border = BorderStroke(1.dp, JathrBorder),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = uiState.newFollowUpsCount.toString(),
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrPrimary,
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = stringResource(R.string.new_follow_ups_title),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = JathrTextSecondary,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }
                }

                // Specialist 2: Today's Appointments Card
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
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = stringResource(R.string.today_appointments_title),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                )
                                TextButton(onClick = onViewScheduleClick) {
                                    Text(
                                        text = stringResource(R.string.schedule_button),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = JathrPrimary,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (uiState.todayAppointmentsList.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.no_today_appointments),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = JathrTextSecondary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                )
                            } else {
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    uiState.todayAppointmentsList.forEach { appt ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { onAppointmentClick(appt.id) }
                                                .padding(vertical = 4.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Surface(
                                                shape = RoundedCornerShape(12.dp),
                                                color = JathrLavender,
                                            ) {
                                                Text(
                                                    text = appt.timeText,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = JathrTextMain,
                                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                                )
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(horizontal = 12.dp),
                                            ) {
                                                Text(
                                                    text = appt.clientName,
                                                    style = MaterialTheme.typography.titleMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = JathrTextMain,
                                                )
                                                Text(
                                                    text = appt.serviceType,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = JathrTextSecondary,
                                                )
                                            }

                                            // Status chip
                                            // TODO: color
                                            Surface(
                                                shape = RoundedCornerShape(12.dp),
                                                color = JathrLavender,
                                            ) {
                                                Text(
                                                    text = appt.statusText,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = JathrPrimary,
                                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Specialist 3: Pending Follow-ups Card
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
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = stringResource(R.string.pending_follow_ups_title),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                )
                                TextButton(onClick = onViewAllFollowUpsClick) {
                                    Text(
                                        text = stringResource(R.string.all_button),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = JathrPrimary,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            if (uiState.pendingFollowUpsList.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.no_pending_follow_ups),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = JathrTextSecondary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                )
                            } else {
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    uiState.pendingFollowUpsList.forEach { item ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { onFollowUpItemClick(item.id) }
                                                .padding(vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .background(
                                                        color = JathrPrimary,
                                                        shape = CircleShape,
                                                    ),
                                                contentAlignment = Alignment.Center,
                                            ) {
                                                Text(
                                                    text = item.clientName.trim().take(1).ifEmpty { "س" },
                                                    style = MaterialTheme.typography.titleMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onPrimary,
                                                )
                                            }

                                            Column {
                                                Text(
                                                    text = item.clientName,
                                                    style = MaterialTheme.typography.titleMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = JathrTextMain,
                                                )
                                                Text(
                                                    text = item.detailsText,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = JathrTextSecondary,
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
        }
    }
}

@Preview(showBackground = true, locale = "ar", heightDp = 1400)
@Composable
fun SharedHomeScreenClientPreview() {
    JatherTheme {
        SharedHomeScreen(
            uiState = SharedHomeUiState(
                isSpecialist = false,
                userName = "سارة",
                hasUnreadNotifications = true,
                nextAppointment = NextAppointmentData(
                    id = "1",
                    specialistName = "نوره الشهري",
                    specialistRole = "أخصائية تغذية علاجية",
                    dateText = "الأحد، ١١ أكتوبر",
                    timeText = "٦:٣٠ م",
                    statusText = "مؤكد",
                ),
                weightProgress = WeightProgressData(
                    currentWeight = "٧٠٫٢ كجم",
                    adherenceText = "الالتزام ٩٠٪ هذا الأسبوع",
                    progressPercentText = "٤٨٪ من الطريق",
                    progressFraction = 0.48f,
                    startWeightText = "البداية ٧٤ كجم",
                    lostWeightText = "خسرت ٣٫٨ من ٨ كجم",
                    goalWeightText = "الهدف ٦٦ كجم",
                ),
                latestNote = SpecialistNoteData(
                    specialistName = "نوره الشهري",
                    timeText = "أمس، ٥:١٠ م",
                    noteText = "أحسنت هذا الأسبوع، زيدي شرب الماء إلى ٣ لتر يوميًا وأضيفي المشي ٣٠ دقيقة بعد العشاء.",
                ),
                progressOverview = ProgressOverviewData(
                    weeklyChange = "-٠٫٧",
                    weeksCount = "٦",
                    adherencePercent = "٩٠٪",
                ),
            ),
            onRoleSelected = {},
            onNotificationsClick = {},
            onNextAppointmentClick = {},
            onViewPlanClick = {},
            onViewProgressClick = {},
            onBookAppointmentClick = {},
            onFilesClick = {},
            onFollowUpClick = {},
            onViewScheduleClick = {},
            onViewAllFollowUpsClick = {},
            onAppointmentClick = {},
            onFollowUpItemClick = {},
        )
    }
}

@Preview(showBackground = true, locale = "ar", heightDp = 1400)
@Composable
fun SharedHomeScreenSpecialistPreview() {
    JatherTheme {
        SharedHomeScreen(
            uiState = SharedHomeUiState(
                isSpecialist = true,
                userName = "سارة",
                hasUnreadNotifications = true,
                todayAppointmentsCount = 3,
                activeClientsCount = 12,
                newFollowUpsCount = 4,
                todayAppointmentsList = listOf(
                    AppointmentItemData("1", "سارة العنزي", "متابعة · إنقاص الوزن", "٤:٠٠ م", "مؤكد"),
                    AppointmentItemData("2", "ريم الدوسري", "استشارة أولى", "٥:١٥ م", "مؤكد"),
                    AppointmentItemData("3", "محمد الحربي", "مراجعة الخطة", "٧:٠٠ م", "بانتظار الدفع"),
                ),
                pendingFollowUpsList = listOf(
                    PendingFollowUpData("1", "سارة العنزي", "الوزن ٧٠٫٢ كجم · الالتزام ٩٠٪"),
                    PendingFollowUpData("2", "لينا السبيعي", "الوزن ٦٤٫٥ كجم · الالتزام ٧٠٪"),
                ),
            ),
            onRoleSelected = {},
            onNotificationsClick = {},
            onNextAppointmentClick = {},
            onViewPlanClick = {},
            onViewProgressClick = {},
            onBookAppointmentClick = {},
            onFilesClick = {},
            onFollowUpClick = {},
            onViewScheduleClick = {},
            onViewAllFollowUpsClick = {},
            onAppointmentClick = {},
            onFollowUpItemClick = {},
        )
    }
}

@Preview(showBackground = true, locale = "ar", heightDp = 1400)
@Composable
fun SharedHomeScreenSpecialistEmptyPreview() {
    JatherTheme {
        SharedHomeScreen(
            uiState = SharedHomeUiState(isSpecialist = true),
            onRoleSelected = {},
            onNotificationsClick = {},
            onNextAppointmentClick = {},
            onViewPlanClick = {},
            onViewProgressClick = {},
            onBookAppointmentClick = {},
            onFilesClick = {},
            onFollowUpClick = {},
            onViewScheduleClick = {},
            onViewAllFollowUpsClick = {},
            onAppointmentClick = {},
            onFollowUpItemClick = {},
        )
    }
}

@Preview(showBackground = true, locale = "ar", heightDp = 1400)
@Composable
fun SharedHomeScreenEmptyPreview() {
    JatherTheme {
        SharedHomeScreen(
            uiState = SharedHomeUiState(),
            onRoleSelected = {},
            onNotificationsClick = {},
            onNextAppointmentClick = {},
            onViewPlanClick = {},
            onViewProgressClick = {},
            onBookAppointmentClick = {},
            onFilesClick = {},
            onFollowUpClick = {},
            onViewScheduleClick = {},
            onViewAllFollowUpsClick = {},
            onAppointmentClick = {},
            onFollowUpItemClick = {},
        )
    }
}
