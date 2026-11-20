package com.example.jather.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.screens.AccountScreen
import com.example.jather.ui.screens.AccountUiState
import com.example.jather.ui.screens.AvailabilityScreen
import com.example.jather.ui.screens.AvailabilityUiState
import com.example.jather.ui.screens.EditProfileScreen
import com.example.jather.ui.screens.EditProfileUiState
import com.example.jather.ui.screens.SharedHomeScreen
import com.example.jather.ui.screens.SharedHomeUiState
import androidx.compose.ui.res.stringResource
import com.example.jather.R
import com.example.jather.ui.screens.DayAvailabilityUi

// ---------- Navigation functions ----------

fun NavController.navigateToEditProfile() = navigate(Screen.EditProfile.route)
fun NavController.navigateToAvailability() = navigate(Screen.Availability.route)
fun NavController.navigateToNotifications() = navigate(Screen.Notifications.route)
fun NavController.navigateToMedicalFiles() = navigate(Screen.MedicalFiles.route)
fun NavController.navigateToWeeklyFollowUp() = navigate(Screen.WeeklyFollowUp.route)
fun NavController.navigateToProgress() = navigate(Screen.Progress.route)
fun NavController.navigateToNutritionPlan() = navigate(Screen.NutritionPlan.route)
fun NavController.navigateToBookAppointment() = navigate(Screen.BookAppointment.route)

// ---------- Routes (navigation only; state and logic come in the next phase) ----------



@Composable
private fun SharedHomeRoute(navController: NavController) {
    // TODO: logic - I will write it (replace with ViewModel and real role from Firestore)
    val uiState = SharedHomeUiState().copy(isSpecialist = SessionRole.isSpecialist)

    JathrScaffold(title = "", navController = navController, showTopBar = false) { padding ->
        SharedHomeScreen(
            uiState = uiState,
            onNotificationsClick = { navController.navigateToNotifications() },
            onNextAppointmentClick = {}, // TODO: logic - I will write it
            onViewPlanClick = { navController.navigateToNutritionPlan() },
            onViewProgressClick = { navController.navigateToProgress() },
            onBookAppointmentClick = { navController.navigateToBookAppointment() },
            onFilesClick = { navController.navigateToMedicalFiles() },
            onFollowUpClick = { navController.navigateToWeeklyFollowUp() },
            onViewScheduleClick = {}, // TODO: logic - I will write it
            onViewAllFollowUpsClick = { navController.navigateToWeeklyFollowUp() },
            onAppointmentClick = {}, // TODO: logic - I will write it
            onFollowUpItemClick = {}, // TODO: logic - I will write it
            modifier = Modifier.padding(padding)
        )
    }
}

@Composable
private fun AccountRoute(navController: NavController) {
    // TODO: logic - I will write it (replace with ViewModel and real user data from Firestore)
    var uiState by remember { mutableStateOf(AccountUiState()) }

    JathrScaffold(title = "", navController = navController, showTopBar = false) { padding ->
        AccountScreen(
            uiState = uiState.copy(isSpecialist = SessionRole.isSpecialist),
            onFullNameChange = { uiState = uiState.copy(fullName = it) },
            onPhoneChange = { uiState = uiState.copy(phone = it) },
            onSaveClick = {}, // TODO: logic - I will write it
            onEditHealthClick = { navController.navigateToEditProfile() },
            onEditProfessionalClick = { navController.navigateToEditProfile() },
            onMedicalFilesClick = { navController.navigateToMedicalFiles() },
            onNotificationsClick = { navController.navigateToNotifications() },
            onFollowUpLogClick = { navController.navigateToWeeklyFollowUp() },
            onAvailabilityClick = { navController.navigateToAvailability() },
            onLogoutClick = {
                navController.navigate(Screen.Login.route) { popUpTo(0) }
            },
            modifier = Modifier.padding(padding)
        )
    }
}
@Composable
private fun EditProfileRoute(navController: NavController) {
    // TODO: logic - I will write it (replace with ViewModel, validation and saving to Firestore)
    var uiState by remember {
        mutableStateOf(EditProfileUiState(isSpecialist = SessionRole.isSpecialist))
    }

    JathrScaffold(
        title = "", navController = navController,
        showTopBar = false, showBottomBar = false
    ) { padding ->
        EditProfileScreen(
            uiState = uiState,
            onFullNameChange = { uiState = uiState.copy(fullName = it) },
            onPhoneChange = { uiState = uiState.copy(phone = it) },
            onEmailChange = { uiState = uiState.copy(email = it) },
            onGenderSelected = { uiState = uiState.copy(gender = it) },
            onBirthDateChange = { uiState = uiState.copy(birthDate = it) },
            onHeightChange = { uiState = uiState.copy(heightCm = it) },
            onSpecializationChange = { uiState = uiState.copy(specialization = it) },
            onQualificationChange = { uiState = uiState.copy(qualification = it) },
            onCertificatesChange = { uiState = uiState.copy(certificates = it) },
            onYearsChange = { uiState = uiState.copy(yearsOfExperience = it) },
            onWorkplaceChange = { uiState = uiState.copy(workplace = it) },
            onWeightChange = { uiState = uiState.copy(weightKg = it) },
            onGoalChange = { uiState = uiState.copy(goal = it) },
            onChronicConditionsChange = { uiState = uiState.copy(chronicConditions = it) },
            onAllergiesChange = { uiState = uiState.copy(allergies = it) },
            onSave = { navController.popBackStack() }, // TODO: logic - I will write it (save first)
            onBackClick = { navController.popBackStack() },
            modifier = Modifier.padding(padding)
        )
    }
}
@Composable
private fun AvailabilityRoute(navController: NavController) {
    // TODO: logic - I will write it (replace with ViewModel, real data from Firestore and saving)
    val dayLabels = listOf(
        stringResource(R.string.day_sunday),
        stringResource(R.string.day_monday),
        stringResource(R.string.day_tuesday),
        stringResource(R.string.day_wednesday),
        stringResource(R.string.day_thursday),
        stringResource(R.string.day_friday),
        stringResource(R.string.day_saturday)
    )
    var uiState by remember {
        mutableStateOf(
            AvailabilityUiState(
                days = dayLabels.mapIndexed { index, label ->
                    DayAvailabilityUi(id = index.toString(), dayLabel = label)
                }
            )
        )
    }

    JathrScaffold(
        title = "", navController = navController,
        showTopBar = false, showBottomBar = false
    ) { padding ->
        AvailabilityScreen(
            uiState = uiState,
            onBackClick = { navController.popBackStack() },
            onDayToggle = { id ->
                uiState = uiState.copy(
                    days = uiState.days.map {
                        if (it.id == id) it.copy(isAvailable = !it.isAvailable) else it
                    }
                )
            },
            onStartTimeSelected = { id, time ->
                uiState = uiState.copy(
                    days = uiState.days.map { if (it.id == id) it.copy(startTime = time) else it }
                )
            },
            onEndTimeSelected = { id, time ->
                uiState = uiState.copy(
                    days = uiState.days.map { if (it.id == id) it.copy(endTime = time) else it }
                )
            },
            onSaveClick = { navController.popBackStack() }, // TODO: logic - I will write it (save first)
            modifier = Modifier.padding(padding)
        )
    }
}

// ---------- Graph ----------

fun NavGraphBuilder.profileGraph(navController: NavController) {
    composable(Screen.Home.route) { SharedHomeRoute(navController) }
    composable(Screen.Profile.route) { AccountRoute(navController) }
    composable(Screen.EditProfile.route) { EditProfileRoute(navController) }
    composable(Screen.Availability.route) { AvailabilityRoute(navController) }
}