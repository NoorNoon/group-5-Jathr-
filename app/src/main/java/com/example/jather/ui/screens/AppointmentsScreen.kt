package com.example.jather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.components.JathrScaffold
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary
import com.example.jather.data.DemoData

@Composable
fun AppointmentsScreen(navController: NavController) {
    JathrScaffold(
        title = "مواعيدي",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("المواعيد القادمة", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = JathrTextMain)
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("استشارة مع ${DemoData.SPECIALIST_NAME}", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = JathrPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("التاريخ: 2026/11/22 - الساعة 04:00 مساءً", fontSize = 14.sp, color = JathrTextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("الحالة: مؤكد", fontSize = 14.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(Screen.BookAppointment.route) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("حجز موعد جديد", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

