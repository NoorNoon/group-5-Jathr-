package com.example.jather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.jather.ui.screens.AccountScreen
import com.example.jather.ui.screens.AccountUiState
import com.example.jather.ui.screens.AvailabilityScreen
import com.example.jather.ui.screens.AvailabilityUiState
import com.example.jather.ui.screens.EditProfileScreen
import com.example.jather.ui.screens.EditProfileUiState
import com.example.jather.ui.screens.SharedHomeScreen
import com.example.jather.ui.screens.SharedHomeUiState

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
    SharedHomeScreen(
        uiState = SharedHomeUiState(),
        onRoleSelected = {}, // TODO: logic - I will write it
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
        onFollowUpItemClick = {} // TODO: logic - I will write it
    )
}

@Composable
private fun AccountRoute(navController: NavController) {
    AccountScreen(
        uiState = AccountUiState(),
        onFullNameChange = {}, // TODO: logic - I will write it
        onPhoneChange = {}, // TODO: logic - I will write it
        onSaveClick = {}, // TODO: logic - I will write it
        onEditHealthClick = { navController.navigateToEditProfile() },
        onMedicalFilesClick = { navController.navigateToMedicalFiles() },
        onNotificationsClick = { navController.navigateToNotifications() },
        onFollowUpLogClick = { navController.navigateToWeeklyFollowUp() },
        onAvailabilityClick = { navController.navigateToAvailability() },
        onLogoutClick = {
            navController.navigate(Screen.Login.route) { popUpTo(0) }
        }
    )
}

@Composable
private fun EditProfileRoute(navController: NavController) {
    EditProfileScreen(
        uiState = EditProfileUiState(),
        onRoleSelected = {}, // TODO: logic - I will write it
        onFullNameChange = {}, // TODO: logic - I will write it
        onPhoneChange = {}, // TODO: logic - I will write it
        onEmailChange = {}, // TODO: logic - I will write it
        onGenderClick = {}, // TODO: logic - I will write it
        onBirthDateChange = {}, // TODO: logic - I will write it
        onHeightChange = {}, // TODO: logic - I will write it
        onSpecializationChange = {}, // TODO: logic - I will write it
        onQualificationChange = {}, // TODO: logic - I will write it
        onCertificatesChange = {}, // TODO: logic - I will write it
        onYearsChange = {}, // TODO: logic - I will write it
        onWorkplaceChange = {}, // TODO: logic - I will write it
        onSave = { navController.popBackStack() },
        onBackClick = { navController.popBackStack() }
    )
}

@Composable
private fun AvailabilityRoute(navController: NavController) {
    AvailabilityScreen(
        uiState = AvailabilityUiState(),
        onBackClick = { navController.popBackStack() },
        onDayClick = {}, // TODO: logic - I will write it
        onTimeRangeClick = {}, // TODO: logic - I will write it
        onSaveClick = { navController.popBackStack() }
    )
}

// ---------- Graph ----------

fun NavGraphBuilder.profileGraph(navController: NavController) {
    composable(Screen.Home.route) { SharedHomeRoute(navController) }
    composable(Screen.Profile.route) { AccountRoute(navController) }
    composable(Screen.EditProfile.route) { EditProfileRoute(navController) }
    composable(Screen.Availability.route) { AvailabilityRoute(navController) }
}