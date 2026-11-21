package com.example.jather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

data class EditProfileUiState(
    val isSpecialist: Boolean = false,
    val fullName: String = "",
    val phone: String = "",
    val email: String = "",
    val gender: String = "",
    val birthDate: String = "",
    val heightCm: String = "",
    val weightKg: String = "",
    val goal: String = "",
    val chronicConditions: String = "",
    val allergies: String = "",
    val specialization: String = "",
    val qualification: String = "",
    val certificates: String = "",
    val yearsOfExperience: String = "",
    val workplace: String = "",
)

// Programmatic ImageVectors using Compose UI Graphics
private val IconArrowBack: ImageVector = ImageVector.Builder(
    name = "ArrowBack",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2.5f
    ) {
        moveTo(9f, 18f)
        lineTo(15f, 12f)
        lineTo(9f, 6f)
    }
}.build()

private val IconEditPencil: ImageVector = ImageVector.Builder(
    name = "EditPencil",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f
    ) {
        moveTo(3f, 17.25f)
        lineTo(3f, 21f)
        lineTo(6.75f, 21f)
        lineTo(17.81f, 9.94f)
        lineTo(14.06f, 6.19f)
        close()
        moveTo(20.71f, 7.04f)
        lineTo(16.96f, 3.29f)
    }
}.build()

private val IconPersonUser: ImageVector = ImageVector.Builder(
    name = "PersonUser",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f
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

private val IconPhoneReceiver: ImageVector = ImageVector.Builder(
    name = "PhoneReceiver",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f
    ) {
        moveTo(6.62f, 10.79f)
        curveTo(8.06f, 13.62f, 10.38f, 15.94f, 13.21f, 17.38f)
        lineTo(15.41f, 15.18f)
        curveTo(15.68f, 14.91f, 16.08f, 14.82f, 16.43f, 14.94f)
        curveTo(17.55f, 15.31f, 18.76f, 15.51f, 20f, 15.51f)
        lineTo(20f, 19f)
        curveTo(20f, 19.55f, 19.55f, 20f, 19f, 20f)
        curveTo(10.16f, 20f, 4f, 13.84f, 4f, 5f)
        curveTo(4f, 4.45f, 4.45f, 4f, 5f, 4f)
        lineTo(8.5f, 4f)
        curveTo(9.05f, 4f, 9.5f, 4.45f, 9.5f, 5f)
        curveTo(9.5f, 6.24f, 9.7f, 7.45f, 10.07f, 8.57f)
        curveTo(10.18f, 8.92f, 10.1f, 9.31f, 9.82f, 9.59f)
        close()
    }
}.build()

private val IconEmailMail: ImageVector = ImageVector.Builder(
    name = "EmailMail",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f
    ) {
        moveTo(4f, 4f)
        lineTo(20f, 4f)
        lineTo(20f, 18f)
        lineTo(4f, 18f)
        close()
        moveTo(4f, 4f)
        lineTo(12f, 11f)
        lineTo(20f, 4f)
    }
}.build()

private val IconCalendarDate: ImageVector = ImageVector.Builder(
    name = "CalendarDate",
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

private val IconCheckMark: ImageVector = ImageVector.Builder(
    name = "CheckMark",
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
).apply {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2.5f
    ) {
        moveTo(5f, 12f)
        lineTo(10f, 17f)
        lineTo(19f, 6f)
    }
}.build()

@Composable
fun EditProfileScreen(
    uiState: EditProfileUiState,
    onFullNameChange: (String) -> Unit = {},
    onPhoneChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onGenderSelected: (String) -> Unit = {},
    onGenderClick: () -> Unit = {},
    onBirthDateChange: (String) -> Unit = {},
    onHeightChange: (String) -> Unit = {},
    onWeightChange: (String) -> Unit = {},
    onGoalChange: (String) -> Unit = {},
    onChronicConditionsChange: (String) -> Unit = {},
    onAllergiesChange: (String) -> Unit = {},
    onSpecializationChange: (String) -> Unit = {},
    onQualificationChange: (String) -> Unit = {},
    onCertificatesChange: (String) -> Unit = {},
    onYearsChange: (String) -> Unit = {},
    onWorkplaceChange: (String) -> Unit = {},
    onSave: () -> Unit = {},
    onBackClick: () -> Unit = {},
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

            // 2. Header Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                imageVector = IconEditPencil,
                                contentDescription = stringResource(R.string.edit_profile_title),
                                tint = JathrPrimary,
                                modifier = Modifier.size(24.dp),
                            )
                            Text(
                                text = stringResource(R.string.edit_profile_title),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = JathrTextMain,
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = stringResource(R.string.edit_profile_subtitle),
                            style = MaterialTheme.typography.bodyMedium,
                            color = JathrTextSecondary,
                        )
                    }
                }
            }

            // 4. Form Fields (leadingIcon sits on the right in RTL layout)
            if (!uiState.isSpecialist) {
                item {
                    EditProfileField(
                        value = uiState.fullName,
                        onValueChange = onFullNameChange,
                        labelRes = R.string.full_name_label,
                        icon = IconPersonUser,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.phone,
                        onValueChange = onPhoneChange,
                        labelRes = R.string.phone_label,
                        icon = IconPhoneReceiver,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.email,
                        onValueChange = onEmailChange,
                        labelRes = R.string.email_label,
                        icon = IconEmailMail,
                    )
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.gender_label),
                            style = MaterialTheme.typography.bodyLarge,
                            color = JathrTextMain,
                        )

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
                            val femaleText = stringResource(R.string.gender_picker_female)
                            val maleText = stringResource(R.string.gender_picker_male)

                            val isFemaleSelected = uiState.gender == femaleText || uiState.gender == "أنثى"
                            val isMaleSelected = uiState.gender == maleText || uiState.gender == "ذكر"

                            // Female Button
                            Surface(
                                onClick = {
                                    // TODO: logic - I will write it
                                    onGenderSelected(femaleText)
                                    onGenderClick()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = if (isFemaleSelected) JathrPrimary else JathrBorder.copy(alpha = 0.3f),
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = femaleText,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isFemaleSelected) MaterialTheme.colorScheme.onPrimary else JathrTextSecondary,
                                    )
                                }
                            }

                            // Male Button
                            Surface(
                                onClick = {
                                    // TODO: logic - I will write it
                                    onGenderSelected(maleText)
                                    onGenderClick()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = if (isMaleSelected) JathrPrimary else JathrBorder.copy(alpha = 0.3f),
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = maleText,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isMaleSelected) MaterialTheme.colorScheme.onPrimary else JathrTextSecondary,
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    EditProfileField(
                        value = uiState.birthDate,
                        onValueChange = onBirthDateChange,
                        labelRes = R.string.birth_date_label,
                        icon = IconCalendarDate,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.heightCm,
                        onValueChange = onHeightChange,
                        labelRes = R.string.height_label,
                        icon = IconPersonUser,
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.health_info_section),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = JathrTextMain,
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.weightKg,
                        onValueChange = onWeightChange,
                        labelRes = R.string.weight_label,
                        icon = IconPersonUser,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.goal,
                        onValueChange = onGoalChange,
                        labelRes = R.string.goal_label,
                        icon = IconPersonUser,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.chronicConditions,
                        onValueChange = onChronicConditionsChange,
                        labelRes = R.string.chronic_conditions_label,
                        icon = IconPersonUser,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.allergies,
                        onValueChange = onAllergiesChange,
                        labelRes = R.string.allergies_label,
                        icon = IconPersonUser,
                    )
                }
            } else {
                item {
                    EditProfileField(
                        value = uiState.fullName,
                        onValueChange = onFullNameChange,
                        labelRes = R.string.full_name_label,
                        icon = IconPersonUser,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.phone,
                        onValueChange = onPhoneChange,
                        labelRes = R.string.phone_label,
                        icon = IconPhoneReceiver,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.email,
                        onValueChange = onEmailChange,
                        labelRes = R.string.email_label,
                        icon = IconEmailMail,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.specialization,
                        onValueChange = onSpecializationChange,
                        labelRes = R.string.specialization_label,
                        icon = IconPersonUser,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.qualification,
                        onValueChange = onQualificationChange,
                        labelRes = R.string.qualification_label,
                        icon = IconPersonUser,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.certificates,
                        onValueChange = onCertificatesChange,
                        labelRes = R.string.certificates_label,
                        icon = IconPersonUser,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.yearsOfExperience,
                        onValueChange = onYearsChange,
                        labelRes = R.string.years_of_experience_label,
                        icon = IconCalendarDate,
                    )
                }
                item {
                    EditProfileField(
                        value = uiState.workplace,
                        onValueChange = onWorkplaceChange,
                        labelRes = R.string.workplace_label,
                        icon = IconPersonUser,
                    )
                }
            }

            // 5. Save Button
            item {
                Spacer(modifier = Modifier.height(6.dp))

                // TODO: logic - I will write it
                Button(
                    onClick = onSave,
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
                        Icon(
                            imageVector = IconCheckMark,
                            contentDescription = stringResource(R.string.save_changes_button),
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
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
    }
}

@Composable
private fun EditProfileField(
    value: String,
    onValueChange: (String) -> Unit,
    labelRes: Int,
    icon: ImageVector,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = stringResource(labelRes),
                style = MaterialTheme.typography.bodyLarge,
                color = JathrTextMain,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(labelRes),
                tint = JathrPrimary,
            )
        },
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

@Preview(showBackground = true, locale = "ar")
@Composable
fun EditProfileScreenClientPreview() {
    JatherTheme {
        EditProfileScreen(
            uiState = EditProfileUiState(
                isSpecialist = false,
                fullName = "أحمد محمد",
                phone = "0501234567",
                email = "ahmad@example.com",
                gender = "أنثى",
                birthDate = "1995/04/12",
                heightCm = "175",
                weightKg = "70.2",
                goal = "إنقاص الوزن",
                chronicConditions = "ضغط الدم",
                allergies = "الفول السوداني",
            ),
            onFullNameChange = {},
            onPhoneChange = {},
            onEmailChange = {},
            onGenderSelected = {},
            onBirthDateChange = {},
            onHeightChange = {},
            onWeightChange = {},
            onGoalChange = {},
            onChronicConditionsChange = {},
            onAllergiesChange = {},
            onSpecializationChange = {},
            onQualificationChange = {},
            onCertificatesChange = {},
            onYearsChange = {},
            onWorkplaceChange = {},
            onSave = {},
            onBackClick = {},
        )
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
fun EditProfileScreenSpecialistPreview() {
    JatherTheme {
        EditProfileScreen(
            uiState = EditProfileUiState(
                isSpecialist = true,
                fullName = "د. سارة التميمي",
                phone = "0559876543",
                email = "sara@example.com",
                specialization = "تغذية علاجية",
                qualification = "بكالوريوس علوم التغذية",
                certificates = "شهادة هيئة التخصصات الصحية",
                yearsOfExperience = "5 سنوات",
                workplace = "مستشفى الملك فيصل",
            ),
            onFullNameChange = {},
            onPhoneChange = {},
            onEmailChange = {},
            onGenderSelected = {},
            onBirthDateChange = {},
            onHeightChange = {},
            onWeightChange = {},
            onGoalChange = {},
            onChronicConditionsChange = {},
            onAllergiesChange = {},
            onSpecializationChange = {},
            onQualificationChange = {},
            onCertificatesChange = {},
            onYearsChange = {},
            onWorkplaceChange = {},
            onSave = {},
            onBackClick = {},
        )
    }
}
