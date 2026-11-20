package com.example.jather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jather.ui.screens.*

sealed class Screen(val route: String) {
    object Start : Screen("start")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object ForgotPassword : Screen("forgot_password")
    object AssessmentStep1 : Screen("assessment_1")
    object AssessmentStep2 : Screen("assessment_2")
    object AssessmentStep3 : Screen("assessment_3")
    object Home : Screen("home")
    object Appointments : Screen("appointments")
    object NutritionPlan : Screen("nutrition_plan")
    object Profile : Screen("profile")
    object BookAppointment : Screen("book_appointment")
    object Payment : Screen("payment")
    object PaymentFailed : Screen("payment_failed")
    object BookingConfirmation : Screen("booking_confirmation")
    object WeeklyFollowUp : Screen("weekly_follow_up")
    object Progress : Screen("progress")
    object Notifications : Screen("notifications")
    object MedicalFiles : Screen("medical_files")
    object EditProfile : Screen("edit_profile")
    object RateSession : Screen("rate_session")
    object Packages : Screen("packages")
    object NutritionPlans : Screen("nutrition_plans")
    object PlanEditor : Screen("plan_editor/{clientId}") {
        fun createRoute(clientId: Int) = "plan_editor/$clientId"
    }
    object PlanPreview : Screen("plan_preview/{clientId}") {
        fun createRoute(clientId: Int) = "plan_preview/$clientId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "client_medical_files") {
        composable(Screen.Start.route) { StartScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.SignUp.route) { SignUpScreen(navController) }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }
        composable(Screen.AssessmentStep1.route) { AssessmentStep1Screen(navController) }
        composable(Screen.AssessmentStep2.route) { AssessmentStep2Screen(navController) }
        composable(Screen.AssessmentStep3.route) { AssessmentStep3Screen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Appointments.route) { AppointmentsScreen(navController) }
        composable(Screen.NutritionPlan.route) { NutritionPlanScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.BookAppointment.route) { BookAppointmentScreen(navController) }
        composable(Screen.Payment.route) { PaymentScreen(navController) }
        composable(Screen.PaymentFailed.route) { PaymentFailedScreen(navController) }
        composable(Screen.BookingConfirmation.route) { BookingConfirmationScreen(navController) }
        composable(Screen.WeeklyFollowUp.route) { WeeklyFollowUpScreen(navController) }
        composable(Screen.Progress.route) { ProgressScreen(navController) }
        composable(Screen.Notifications.route) { NotificationsScreen(navController) }
        composable(Screen.MedicalFiles.route) { MedicalFilesScreen(navController) }
        composable(Screen.EditProfile.route) { EditProfileScreen(navController) }
        composable(Screen.RateSession.route) { RateSessionScreen(navController) }
        composable(Screen.Packages.route) { PackagesScreen(navController) }
        plansGraph(navController)
        specialistGraph(navController)
    }
}
