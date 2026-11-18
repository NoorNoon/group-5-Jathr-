package com.example.jather.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jather.ui.screens.plans.PlanEditorScreen
import com.example.jather.ui.screens.plans.PlanPreviewScreen
import com.example.jather.ui.screens.plans.PlansListScreen

fun NavController.navigateToPlanEditor(clientId: Int) {
    navigate(Screen.PlanEditor.createRoute(clientId))
}

fun NavController.navigateToPlanPreview(clientId: Int) {
    navigate(Screen.PlanPreview.createRoute(clientId))
}

fun NavController.navigateBackToPlans() {
    popBackStack(Screen.NutritionPlans.route, false)
}

fun NavGraphBuilder.plansGraph(navController: NavController) {

    composable(Screen.NutritionPlans.route) {
        PlansListScreen(navController)
    }

    composable(
        route = Screen.PlanEditor.route,
        arguments = listOf(navArgument("clientId") { type = NavType.IntType })
    ) { backStackEntry ->
        val clientId = backStackEntry.arguments?.getInt("clientId") ?: 0
        PlanEditorScreen(navController, clientId)
    }

    composable(
        route = Screen.PlanPreview.route,
        arguments = listOf(navArgument("clientId") { type = NavType.IntType })
    ) { backStackEntry ->
        val clientId = backStackEntry.arguments?.getInt("clientId") ?: 0
        PlanPreviewScreen(navController, clientId)
    }
}
