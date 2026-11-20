package com.example.jather.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jather.ui.screens.specialist.ClientListScreen
import com.example.jather.ui.screens.specialist.ClientProfileScreen
import com.example.jather.ui.screens.specialist.NutritionAssessmentScreen
import com.example.jather.ui.screens.specialist.SpecialistMedicalFilesScreen
import com.example.jather.ui.screens.specialist.MedicalAnalysisReviewScreen
import com.example.jather.ui.screens.specialist.ClientMedicalFilesScreen

fun NavController.navigateToSpecialistClients() {
    navigate("specialist_clients")
}

fun NavController.navigateToSpecialistClient(clientId: Int) {
    navigate("specialist_client/$clientId")
}

fun NavController.navigateToSpecialistAssessment(clientId: Int) {
    navigate("specialist_assessment/$clientId")
}

fun NavController.navigateToSpecialistFiles(clientId: Int) {
    navigate("specialist_files/$clientId")
}

fun NavController.navigateToSpecialistAnalysisReview(fileId: Int) {
    navigate("specialist_analysis_review/$fileId")
}

fun NavGraphBuilder.specialistGraph(navController: NavController) {
    composable("client_medical_files") { ClientMedicalFilesScreen(navController) }
    composable("specialist_clients") {
        ClientListScreen(navController)
    }

    composable(
        route = "specialist_client/{clientId}",
        arguments = listOf(navArgument("clientId") { type = NavType.IntType })
    ) { backStackEntry ->
        val clientId = backStackEntry.arguments?.getInt("clientId") ?: 1
        ClientProfileScreen(navController, clientId)
    }

    composable(
        route = "specialist_assessment/{clientId}",
        arguments = listOf(navArgument("clientId") { type = NavType.IntType })
    ) { backStackEntry ->
        val clientId = backStackEntry.arguments?.getInt("clientId") ?: 1
        NutritionAssessmentScreen(navController, clientId)
    }

    composable(
        route = "specialist_files/{clientId}",
        arguments = listOf(navArgument("clientId") { type = NavType.IntType })
    ) { backStackEntry ->
        val clientId = backStackEntry.arguments?.getInt("clientId") ?: 1
        SpecialistMedicalFilesScreen(navController, clientId)
    }

    composable(
        route = "specialist_analysis_review/{fileId}",
        arguments = listOf(navArgument("fileId") { type = NavType.IntType })
    ) { backStackEntry ->
        val fileId = backStackEntry.arguments?.getInt("fileId") ?: 1
        MedicalAnalysisReviewScreen(navController, fileId)
    }
}
