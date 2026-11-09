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

@Composable
fun ProfileScreen(navController: NavController) {
    JathrScaffold(
        title = "الملف الشخصي",
        navController = navController,
        showBackButton = true,
        showBottomBar = true
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp)
        ) {
            Text("معلومات الحساب", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = JathrTextMain)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = "أحمد محمد",
                onValueChange = {},
                label = { Text("الاسم") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = "ahmad@example.com",
                onValueChange = {},
                label = { Text("البريد الإلكتروني") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(Screen.EditProfile.route) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("تعديل الملف الشخصي", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.Start.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("تسجيل الخروج", color = Color(0xFFC62828), fontSize = 16.sp)
            }
        }
    }
}
