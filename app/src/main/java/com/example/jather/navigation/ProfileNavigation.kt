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
import com.example.jather.data.DemoData
import com.example.jather.data.PlanRepository
import com.example.jather.ui.screens.AppointmentItemData
import com.example.jather.ui.screens.NextAppointmentData
import com.example.jather.ui.screens.PendingFollowUpData
import com.example.jather.ui.screens.ProgressOverviewData
import com.example.jather.ui.screens.SpecialistNoteData
import com.example.jather.ui.screens.WeightProgressData
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



private fun currentClientName(): String = DemoData.currentClientName

// Demo data for the home screen (same people as DemoData, and the client's note comes
// from the plan the specialist published). Replace with real data from Firebase later.
private fun demoHomeState(isSpecialist: Boolean): SharedHomeUiState {
    if (isSpecialist) {
        return SharedHomeUiState(
            isSpecialist = true,
            todayAppointmentsCount = 3,
            activeClientsCount = DemoData.clients.size,
            newFollowUpsCount = 2,
            todayAppointmentsList = listOf(
                AppointmentItemData("1", DemoData.clientName(4), "متابعة · زيادة الوزن", "٤:٠٠ م", "مؤكد"),
                AppointmentItemData("2", DemoData.clientName(3), "مراجعة الخطة", "٥:١٥ م", "مؤكد"),
                AppointmentItemData("3", DemoData.clientName(6), "استشارة أولى", "٧:٠٠ م", "بانتظار الدفع")
            ),
            pendingFollowUpsList = listOf(
                PendingFollowUpData("1", DemoData.clientName(5), "الوزن ٦٤٫٥ كجم · الالتزام ٧٠٪"),
                PendingFollowUpData("2", DemoData.clientName(2), "الوزن ٧٠٫٢ كجم · الالتزام ٩٠٪")
            )
        )
    }
    val planNotes = PlanRepository.latestPublishedPlanFor(DemoData.CURRENT_CLIENT_ID)?.notes.orEmpty()
    return SharedHomeUiState(
        isSpecialist = false,
        nextAppointment = NextAppointmentData(
            id = "2041",
            specialistName = DemoData.SPECIALIST_NAME,
            specialistRole = "أخصائية تغذية علاجية",
            dateText = "الأحد، ٢٢ نوفمبر",
            timeText = "٤:٠٠ م",
            statusText = "مؤكد"
        ),
        weightProgress = WeightProgressData(
            currentWeight = "٧٠٫٢ كجم",
            adherenceText = "الالتزام ٩٠٪ هذا الأسبوع",
            progressPercentText = "٤٨٪ من الطريق",
            progressFraction = 0.48f,
            startWeightText = "البداية ٧٤ كجم",
            lostWeightText = "خسرتِ ٣٫٨ من ٨ كجم",
            goalWeightText = "الهدف ٦٦ كجم"
        ),
        latestNote = SpecialistNoteData(
            specialistName = DemoData.SPECIALIST_NAME,
            timeText = "أمس، ٥:١٠ م",
            noteText = planNotes.ifEmpty { "أحسنتِ هذا الأسبوع، استمري على الخطة." }
        ),
        progressOverview = ProgressOverviewData(
            weeklyChange = "-٠٫٧",
            weeksCount = "٢",
            adherencePercent = "٩٠٪"
        )
    )
}

@Composable
private fun SharedHomeRoute(navController: NavController) {
    // TODO: logic - I will write it (replace with ViewModel and real role from Firestore)
    // The demo client is the same one the plans screens use (PlanRepository.CURRENT_CLIENT_ID),
    // so the name on the home screen matches the plan the specialist published.
    val clientFirstName = currentClientName().substringBefore(" ")
    val uiState = if (SessionRole.hasDemoData) {
        demoHomeState(SessionRole.isSpecialist).copy(
            userName = if (SessionRole.isSpecialist) DemoData.SPECIALIST_NAME.removePrefix("أ. ").substringBefore(" ") else clientFirstName
        )
    } else {
        SharedHomeUiState(isSpecialist = SessionRole.isSpecialist) // after Sign Up: empty
    }

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
    var uiState by remember(SessionRole.isSpecialist, SessionRole.hasDemoData) {
        mutableStateOf(
            if (SessionRole.hasDemoData) demoAccountState(SessionRole.isSpecialist)
            else AccountUiState()
        )
    }

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
            onFollowUpLogClick = {
                navController.navigateToWeeklyFollowUp()
            },

            onPackagesClick = {
                navController.navigate(Screen.Packages.route)
            },

            onSubscriptionHistoryClick = {
                navController.navigate(Screen.SubscriptionHistory.route)
            },

            onAvailabilityClick = {
                navController.navigateToAvailability()
            },
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
    var uiState by remember(SessionRole.isSpecialist, SessionRole.hasDemoData) {
        mutableStateOf(
            if (SessionRole.hasDemoData) demoEditProfileState(SessionRole.isSpecialist)
            else EditProfileUiState(isSpecialist = SessionRole.isSpecialist)
        )
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
    var uiState by remember(SessionRole.hasDemoData) {
        mutableStateOf(
            AvailabilityUiState(
                days = dayLabels.mapIndexed { index, label ->
                    // Demo (after Login): Sunday to Thursday, 09:00 - 17:00. After Sign Up: empty.
                    val workDay = SessionRole.hasDemoData && index in 0..4
                    DayAvailabilityUi(
                        id = index.toString(),
                        dayLabel = label,
                        isAvailable = workDay,
                        startTime = if (workDay) "09:00" else "",
                        endTime = if (workDay) "17:00" else ""
                    )
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