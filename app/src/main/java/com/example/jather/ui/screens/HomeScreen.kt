package com.example.jather.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary

data class MenuOption(val title: String, val route: String)

@Composable
fun HomeScreen(navController: NavController) {
    val menuOptions = listOf(
        MenuOption("مواعيدي", Screen.Appointments.route),
        MenuOption("الخطة الغذائية", Screen.NutritionPlan.route),
        MenuOption("حجز موعد جديد", Screen.BookAppointment.route),
        MenuOption("المتابعة الأسبوعية", Screen.WeeklyFollowUp.route),
        MenuOption("تقدمي ووزني", Screen.Progress.route),
        MenuOption("الملفات الطبية", Screen.MedicalFiles.route),
        MenuOption("الباقات المتاحة", Screen.Packages.route),
        MenuOption("الإشعارات", Screen.Notifications.route),
        MenuOption("الملف الشخصي", Screen.Profile.route)
    )

    JathrScaffold(
        title = "الرئيسية",
        navController = navController,
        showBackButton = false,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "أهلاً بك، أحمد!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = JathrTextMain
            )
            Text(
                text = "تابع خطتك الغذائية واستشر أخصائيك بكل سهولة",
                fontSize = 14.sp,
                color = JathrTextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(menuOptions) { option ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable { navController.navigate(option.route) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = JathrPrimary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}
