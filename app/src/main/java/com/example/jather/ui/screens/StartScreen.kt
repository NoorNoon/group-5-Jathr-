package com.example.jather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jather.navigation.Screen
import com.example.jather.ui.theme.JathrPrimary
import com.example.jather.ui.theme.JathrLavender
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary

@Composable
fun StartScreen(navController: NavController) {
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
            // App Title / Logo placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(JathrLavender, shape = MaterialTheme.shapes.extraLarge),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "جذر",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = JathrPrimary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "تطبيق جذر للرعاية الغذائية",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = JathrTextMain,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "استشارات غذائية ومتابعة دورية بينك وبين أخصائي التغذية الخاص بك بسهولة ويسر.",
                fontSize = 14.sp,
                color = JathrTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text(text = "تسجيل الدخول", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = { navController.navigate(Screen.SignUp.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "إنشاء حساب جديد", fontSize = 16.sp, color = JathrPrimary)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { navController.navigate(Screen.AssessmentStep1.route) }
            ) {
                Text(text = "البدء بالتقييم الغذائي السريع", fontSize = 14.sp, color = JathrTextSecondary)
            }
        }
    }
}
