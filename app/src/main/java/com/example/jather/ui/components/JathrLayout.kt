package com.example.jather.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jather.navigation.Screen
import com.example.jather.ui.theme.JathrLavender
import com.example.jather.ui.theme.JathrPrimary

data class BottomNavItem(
    val label: String,
    val route: String,
    val iconEmoji: String
)

val bottomNavItems = listOf(
    BottomNavItem("الرئيسية", Screen.Home.route, "🏠"),
    BottomNavItem("مواعيدي", Screen.Appointments.route, "📅"),
    BottomNavItem("الخطة الغذائية", Screen.NutritionPlan.route, "🥗"),
    BottomNavItem("الملف الشخصي", Screen.Profile.route, "👤")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JathrScaffold(
    title: String,
    navController: NavController,
    showBackButton: Boolean = true,
    showBottomBar: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = title,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "جذر Jathr",
                            color = JathrLavender,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = JathrPrimary),
                navigationIcon = {
                    if (showBackButton && currentRoute != Screen.Home.route && currentRoute != Screen.Start.route) {
                        TextButton(onClick = { navController.popBackStack() }) {
                            Text("عودة", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (showBottomBar && currentRoute != Screen.Start.route && currentRoute != Screen.Login.route && currentRoute != Screen.SignUp.route && currentRoute != Screen.ForgotPassword.route) {
                NavigationBar(
                    containerColor = JathrLavender,
                    contentColor = JathrPrimary
                ) {
                    bottomNavItems.forEach { item ->
                        val isSelected = currentRoute == item.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) JathrPrimary else Color.DarkGray
                                )
                            },
                            icon = {
                                Text(
                                    text = item.iconEmoji,
                                    fontSize = 18.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.White
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
