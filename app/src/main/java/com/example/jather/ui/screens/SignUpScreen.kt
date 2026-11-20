package com.example.jather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary
import com.example.jather.navigation.SessionRole
import com.example.jather.ui.components.RoleSelector

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSpecialist by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "إنشاء حساب جديد",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = JathrTextMain
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "انضم إلى عائلة جذر وابدأ رحلتك الصحية",
                fontSize = 14.sp,
                color = JathrTextSecondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            RoleSelector(
                isSpecialist = isSpecialist,
                onRoleSelected = { isSpecialist = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("الاسم الكامل") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("البريد الإلكتروني") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("كلمة المرور") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    SessionRole.isSpecialist = isSpecialist
                    if (isSpecialist) {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Start.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screen.AssessmentStep1.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text(
                    text = if (isSpecialist) "إنشاء الحساب" else "متابعة إلى التقييم الغذائي",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.popBackStack() }) {
                Text(text = "العودة", color = JathrTextSecondary)
            }
        }
    }
}
