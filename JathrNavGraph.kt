package com.example.jathrproject.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.jathrproject.ui.screens.ClientFollowUpScreen
import com.example.jathrproject.ui.screens.ClientProgressScreen
import com.example.jathrproject.ui.screens.FollowUpsListScreen
import com.example.jathrproject.ui.screens.SpecialistReviewScreen
import com.example.jathrproject.ui.theme.DarkPurple
import com.example.jathrproject.ui.theme.LightLavender
import com.example.jathrproject.ui.theme.PrimaryPurple

@Composable
fun JathrNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = JathrRoutes.FollowUpsList,
    initialRole: UserRole = UserRole.CLIENT,
    showDevTestBar: Boolean = SHOW_DEV_TEST_BAR
) {
    var demoRole by remember { mutableStateOf(initialRole) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val testRoutes = listOf(
        Pair(JathrRoutes.FollowUpsList, "المتابعات (27)"),
        Pair(JathrRoutes.ClientFollowUp, "العميل (28)"),
        Pair(JathrRoutes.SpecialistReview, "الأخصائي (29)"),
        Pair(JathrRoutes.ClientProgress, "تقدّمي (30)")
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            if (showDevTestBar) {
                // Development & Testing Navigation Bar (Can be hidden by setting SHOW_DEV_TEST_BAR = false in JathrRoutes.kt)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkPurple)
                        .statusBarsPadding()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "التنقل للاختبار:",
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 2.dp)
                        )

                        // Role Switcher Button
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (demoRole == UserRole.CLIENT) Color(0xFF2E7D4F) else Color(0xFF9A6B00))
                                .clickable {
                                    demoRole = if (demoRole == UserRole.CLIENT) UserRole.SPECIALIST else UserRole.CLIENT
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (demoRole == UserRole.CLIENT) "الدور: عميل" else "الدور: أخصائي",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        // Direct Screen Selectors (27, 28, 29, 30)
                        testRoutes.forEach { (route, label) ->
                            val isSelected = currentRoute == route
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) PrimaryPurple else LightLavender.copy(alpha = 0.25f))
                                    .clickable {
                                        if (currentRoute != route) {
                                            navController.navigate(route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Screen 27 — Follow-Up Records List
            composable(JathrRoutes.FollowUpsList) {
                FollowUpsListScreen(
                    onOpenFollowUp = {
                        if (demoRole == UserRole.CLIENT) {
                            navController.navigate(JathrRoutes.ClientFollowUp)
                        } else {
                            navController.navigate(JathrRoutes.SpecialistReview)
                        }
                    },
                    onOpenSpecialistReview = {
                        navController.navigate(JathrRoutes.SpecialistReview)
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }

            // Screen 28 — Client Follow-Up Form
            composable(JathrRoutes.ClientFollowUp) {
                ClientFollowUpScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onSubmitFollowUp = {
                        navController.popBackStack()
                    }
                )
            }

            // Screen 29 — Specialist Follow-Up Review
            composable(JathrRoutes.SpecialistReview) {
                SpecialistReviewScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onConfirmReview = {
                        navController.popBackStack()
                    }
                )
            }

            // Screen 30 — Client Progress Dashboard
            composable(JathrRoutes.ClientProgress) {
                ClientProgressScreen(
                    onNotificationClick = {
                        navController.navigate(JathrRoutes.SpecialistReview)
                    },
                    onHomeClick = { /* Reserved for team Home screen integration */ },
                    onAppointmentsClick = { /* Reserved for team Appointments screen integration */ },
                    onPlanClick = { /* Reserved for team Meal Plan screen integration */ },
                    onProgressClick = { /* Remains on Screen 30 */ },
                    onProfileClick = { /* Reserved for team Profile screen integration */ }
                )
            }
        }
    }
}
