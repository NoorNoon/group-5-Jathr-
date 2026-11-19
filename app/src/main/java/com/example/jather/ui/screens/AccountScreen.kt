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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jather.R
import com.example.jather.ui.theme.*

data class AccountUiState(
    val isSpecialist: Boolean = false,
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val age: String = "",
    val heightWeight: String = "",
    val goal: String = "",
    val chronicConditions: String = "",
    val allergies: String = "",
    val medicalFilesCount: Int = 0,
    val newNotificationsCount: Int = 0,
)

private val IconBellNotification: ImageVector = ImageVector.Builder(
    name = "BellNotification",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f
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

private val IconListHistory: ImageVector = ImageVector.Builder(
    name = "ListHistory",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f
    ) {
        moveTo(3f, 6f)
        lineTo(21f, 6f)
        moveTo(3f, 12f)
        lineTo(21f, 12f)
        moveTo(3f, 18f)
        lineTo(21f, 18f)
    }
}.build()

private val IconCalendar: ImageVector = ImageVector.Builder(
    name = "Calendar",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f
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

private val IconChevronLeft: ImageVector = ImageVector.Builder(
    name = "ChevronLeft",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f
    ) {
        moveTo(15f, 18f)
        lineTo(9f, 12f)
        lineTo(15f, 6f)
    }
}.build()

@Composable
fun AccountScreen(
    uiState: AccountUiState,
    onFullNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onEditHealthClick: () -> Unit,
    onMedicalFilesClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onFollowUpLogClick: () -> Unit,
    onAvailabilityClick: () -> Unit = {},
    onLogoutClick: () -> Unit,
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
        ) {
            // 1. Top Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = onNotificationsClick,
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp),
                            ),
                    ) {
                        Icon(
                            imageVector = IconBellNotification,
                            contentDescription = stringResource(R.string.notifications_desc),
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

            // 2. Profile Header (Row layout)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Avatar at the start (right side in RTL)
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(
                                color = JathrPrimary,
                                shape = CircleShape,
                            ),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = uiState.fullName.trim().take(1).ifEmpty { "س" },
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    }

                    // Name and Email next to it in a Column
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = uiState.fullName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = JathrTextMain,
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = uiState.email,
                            style = MaterialTheme.typography.bodyMedium,
                            color = JathrTextSecondary,
                        )
                    }
                }
            }

            // 3. Card 1: Personal Information (Client & Specialist)
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
                        Text(
                            text = stringResource(R.string.personal_info_section),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = JathrTextMain,
                        )

                        Column {
                            Text(
                                text = stringResource(R.string.full_name_label),
                                style = MaterialTheme.typography.labelMedium,
                                color = JathrTextSecondary,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = uiState.fullName,
                                onValueChange = onFullNameChange,
                                textStyle = LocalTextStyle.current.copy(color = JathrTextMain)
                                    .merge(MaterialTheme.typography.bodyLarge),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                                    focusedBorderColor = JathrPrimary,
                                    unfocusedBorderColor = JathrBorder,
                                ),
                                singleLine = true,
                            )
                        }

                        Column {
                            Text(
                                text = stringResource(R.string.phone_label),
                                style = MaterialTheme.typography.labelMedium,
                                color = JathrTextSecondary,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = uiState.phone,
                                onValueChange = onPhoneChange,
                                textStyle = LocalTextStyle.current.copy(color = JathrTextMain)
                                    .merge(MaterialTheme.typography.bodyLarge),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                                    focusedBorderColor = JathrPrimary,
                                    unfocusedBorderColor = JathrBorder,
                                ),
                                singleLine = true,
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // TODO: logic - I will write it
                        Button(
                            onClick = onSaveClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = JathrPrimary,
                            ),
                        ) {
                            Text(
                                text = stringResource(R.string.save_changes_button),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    }
                }
            }

            // 4. Card 2: Health Information (Shown ONLY when !isSpecialist)
            if (!uiState.isSpecialist) {
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
                                    text = stringResource(R.string.health_info_section),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = JathrTextMain,
                                )
                                TextButton(onClick = onEditHealthClick) {
                                    Text(
                                        text = stringResource(R.string.edit_button),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = JathrPrimary,
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            HealthInfoRow(
                                label = stringResource(R.string.age_label),
                                value = uiState.age,
                            )
                            HorizontalDivider(color = JathrBorder, thickness = 0.5.dp)

                            HealthInfoRow(
                                label = stringResource(R.string.height_weight_label),
                                value = uiState.heightWeight,
                            )
                            HorizontalDivider(color = JathrBorder, thickness = 0.5.dp)

                            HealthInfoRow(
                                label = stringResource(R.string.goal_label),
                                value = uiState.goal,
                            )
                            HorizontalDivider(color = JathrBorder, thickness = 0.5.dp)

                            HealthInfoRow(
                                label = stringResource(R.string.chronic_conditions_label),
                                value = uiState.chronicConditions,
                            )
                            HorizontalDivider(color = JathrBorder, thickness = 0.5.dp)

                            HealthInfoRow(
                                label = stringResource(R.string.allergies_label),
                                value = uiState.allergies,
                            )
                        }
                    }
                }
            }

            // 5. Card 3: Settings Rows (Client vs Specialist)
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
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        if (!uiState.isSpecialist) {
                            // Client Row 1: Medical Files
                            Surface(
                                onClick = onMedicalFilesClick,
                                color = Color.Transparent,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = stringResource(R.string.my_medical_files),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium,
                                        color = JathrTextMain,
                                    )
                                    Text(
                                        text = stringResource(R.string.files_count_format, uiState.medicalFilesCount),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = JathrTextSecondary,
                                    )
                                }
                            }

                            HorizontalDivider(color = JathrBorder, thickness = 0.5.dp)

                            // Client Row 2: Notifications
                            Surface(
                                onClick = onNotificationsClick,
                                color = Color.Transparent,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = stringResource(R.string.notifications_title),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium,
                                        color = JathrTextMain,
                                    )
                                    Text(
                                        text = stringResource(R.string.notifications_count_format, uiState.newNotificationsCount),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = JathrTextSecondary,
                                    )
                                }
                            }

                            HorizontalDivider(color = JathrBorder, thickness = 0.5.dp)

                            // Client Row 3: Shared Follow-up Log
                            Surface(
                                onClick = {
                                    // TODO: logic - I will write it
                                    onFollowUpLogClick()
                                },
                                color = Color.Transparent,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    ) {
                                        // TODO: icon
                                        Icon(
                                            imageVector = IconListHistory,
                                            contentDescription = stringResource(R.string.shared_followup_log),
                                            tint = JathrPrimary,
                                            modifier = Modifier.size(20.dp),
                                        )
                                        Text(
                                            text = stringResource(R.string.shared_followup_log),
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Medium,
                                            color = JathrTextMain,
                                        )
                                    }

                                    // TODO: icon
                                    Icon(
                                        imageVector = IconChevronLeft,
                                        contentDescription = stringResource(R.string.shared_followup_log),
                                        tint = JathrTextSecondary,
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                            }
                        } else {
                            // Specialist Row 1: Notifications
                            Surface(
                                onClick = onNotificationsClick,
                                color = Color.Transparent,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = stringResource(R.string.notifications_title),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium,
                                        color = JathrTextMain,
                                    )
                                    Text(
                                        text = stringResource(R.string.notifications_count_format, uiState.newNotificationsCount),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = JathrTextSecondary,
                                    )
                                }
                            }

                            HorizontalDivider(color = JathrBorder, thickness = 0.5.dp)

                            // Specialist Row 2: Availability Times
                            Surface(
                                onClick = {
                                    // TODO: logic - I will write it
                                    onAvailabilityClick()
                                },
                                color = Color.Transparent,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    ) {
                                        // TODO: icon
                                        Icon(
                                            imageVector = IconCalendar,
                                            contentDescription = stringResource(R.string.specialist_availability_times),
                                            tint = JathrPrimary,
                                            modifier = Modifier.size(20.dp),
                                        )
                                        Text(
                                            text = stringResource(R.string.specialist_availability_times),
                                            style = MaterialTheme.typography.bodyLarge,
                                            fontWeight = FontWeight.Medium,
                                            color = JathrTextMain,
                                        )
                                    }

                                    // TODO: icon
                                    Icon(
                                        imageVector = IconChevronLeft,
                                        contentDescription = stringResource(R.string.specialist_availability_times),
                                        tint = JathrTextSecondary,
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 6. Logout Button
            item {
                // TODO: logic - I will write it
                OutlinedCard(
                    onClick = onLogoutClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.outlinedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    border = BorderStroke(1.dp, JathrBorder),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = stringResource(R.string.logout_button),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HealthInfoRow(
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = JathrTextSecondary,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = JathrTextMain,
        )
    }
}

@Preview(showBackground = true, locale = "ar", heightDp = 1100)
@Composable
fun AccountScreenSamplePreview() {
    JatherTheme {
        AccountScreen(
            uiState = AccountUiState(
                isSpecialist = false,
                fullName = "سارة العنزي",
                email = "sarah@example.com",
                phone = "0512345678",
                age = "٢٨ سنة",
                heightWeight = "١٦٢ سم / ٧٠٫٢ كجم",
                goal = "إنقاص الوزن",
                chronicConditions = "ضغط الدم",
                allergies = "الفول السوداني",
                medicalFilesCount = 4,
                newNotificationsCount = 2,
            ),
            onFullNameChange = {},
            onPhoneChange = {},
            onSaveClick = {},
            onEditHealthClick = {},
            onMedicalFilesClick = {},
            onNotificationsClick = {},
            onFollowUpLogClick = {},
            onAvailabilityClick = {},
            onLogoutClick = {},
        )
    }
}

@Preview(showBackground = true, locale = "ar", heightDp = 1100)
@Composable
fun AccountSpecialistPreview() {
    JatherTheme {
        AccountScreen(
            uiState = AccountUiState(
                isSpecialist = true,
                fullName = "د. سارة التميمي",
                email = "sara@example.com",
                phone = "0559876543",
                newNotificationsCount = 2,
            ),
            onFullNameChange = {},
            onPhoneChange = {},
            onSaveClick = {},
            onEditHealthClick = {},
            onMedicalFilesClick = {},
            onNotificationsClick = {},
            onFollowUpLogClick = {},
            onAvailabilityClick = {},
            onLogoutClick = {},
        )
    }
}

@Preview(showBackground = true, locale = "ar", heightDp = 1100)
@Composable
fun AccountScreenEmptyPreview() {
    JatherTheme {
        AccountScreen(
            uiState = AccountUiState(),
            onFullNameChange = {},
            onPhoneChange = {},
            onSaveClick = {},
            onEditHealthClick = {},
            onMedicalFilesClick = {},
            onNotificationsClick = {},
            onFollowUpLogClick = {},
            onAvailabilityClick = {},
            onLogoutClick = {},
        )
    }
}
