package com.example.jather.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jather.navigation.Screen
import com.example.jather.navigation.SessionRole
import com.example.jather.ui.theme.JathrLavender
import com.example.jather.ui.theme.JathrPrimary
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val route: String,
    val iconEmoji: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("الرئيسية", Screen.Home.route, "🏠", Icons.Outlined.Home),
    BottomNavItem("مواعيدي", Screen.Appointments.route, "📅", Icons.Outlined.CalendarMonth),
    BottomNavItem("الخطة الغذائية", Screen.NutritionPlan.route, "🥗", Icons.Outlined.RestaurantMenu),
    BottomNavItem("الملف الشخصي", Screen.Profile.route, "👤", Icons.Outlined.Person)
)

// Tabs for a specialist (role chosen at login/sign-up)
val specialistBottomNavItems = listOf(
    BottomNavItem("الرئيسية", Screen.Home.route, "🏠", Icons.Outlined.Home),
    BottomNavItem("العملاء", "specialist_clients", "👥", Icons.Outlined.Groups),
    BottomNavItem("الخطط", Screen.NutritionPlans.route, "🥗", Icons.Outlined.RestaurantMenu),
    BottomNavItem("الجدول", Screen.Availability.route, "📅", Icons.Outlined.CalendarMonth),
    BottomNavItem("حسابي", Screen.Profile.route, "👤", Icons.Outlined.Person)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JathrScaffold(
    title: String,
    navController: NavController,
    showBackButton: Boolean = true,
    showBottomBar: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    showTopBar: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    Scaffold(
        topBar = {
            if (showTopBar) TopAppBar(
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
                        TextButton(onClick = { if (onBackClick != null) onBackClick() else navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("عودة", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (showBottomBar && currentRoute != Screen.Start.route && currentRoute != Screen.Login.route && currentRoute != Screen.SignUp.route && currentRoute != Screen.ForgotPassword.route) {
                JathrBottomBar(navController)
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

/**
 * The one bottom bar used by every screen, so its look and tab order never change.
 * It is always laid out right-to-left (الرئيسية on the right), whatever direction the screen uses,
 * and shows the client or specialist tabs depending on the role chosen at login.
 */
@Composable
fun JathrBottomBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val items = if (SessionRole.isSpecialist) specialistBottomNavItems else bottomNavItems

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        NavigationBar(
            containerColor = JathrLavender,
            contentColor = JathrPrimary
        ) {
            items.forEach { item ->
                val isSelected = isTabSelected(item.route, currentRoute)
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
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) JathrPrimary else Color.DarkGray
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

// Keeps the right tab highlighted on inner screens (a client's profile, the plan editor, ...)
private fun isTabSelected(tabRoute: String, currentRoute: String?): Boolean {
    if (currentRoute == null) return false
    if (currentRoute == tabRoute) return true
    return when (tabRoute) {
        "specialist_clients" -> currentRoute.startsWith("specialist_")
        Screen.NutritionPlans.route -> currentRoute == Screen.PlanEditor.route || currentRoute == Screen.PlanPreview.route
        Screen.Availability.route -> currentRoute == Screen.Availability.route
        else -> false
    }
}
