package com.example.jather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.jather.ui.theme.JathrTextMain
import com.example.jather.ui.theme.JathrTextSecondary

@Composable
fun AssessmentStep1Screen(navController: NavController) {
    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("التقييم الغذائي - الخطوة 1 (القياسات الأساسية)", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = JathrTextMain)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("العمر (سنة)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("الوزن (كجم)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("الطول (سم)") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.navigate(Screen.AssessmentStep2.route) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("التالي", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun AssessmentStep2Screen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("التقييم الغذائي - الخطوة 2 (الهدف الصحي)", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = JathrTextMain)
            Spacer(modifier = Modifier.height(24.dp))

            Text("اختر هدفك الرئيسي:", color = JathrTextSecondary)
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {}, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)) {
                Text("إنقاص الوزن")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("بناء العضلات", color = JathrPrimary)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("الحفاظ على الوزن الصحي", color = JathrPrimary)
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.navigate(Screen.AssessmentStep3.route) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("التالي", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun AssessmentStep3Screen(navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("التقييم الغذائي - الخطوة 3 (النشاط والحساسية)", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = JathrTextMain)
            Spacer(modifier = Modifier.height(24.dp))

            Text("تم إكمال التقييم بنجاح! سيتم تخصيص خطتك الغذائية.", color = JathrTextSecondary, textAlign = TextAlign.Center)

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JathrPrimary)
            ) {
                Text("الانتقال إلى الرئيسية", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
